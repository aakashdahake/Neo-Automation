package com.neonomics.corelibraries;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neonomics.constants.ConstantsRef;
import com.neonomics.constants.Endpoints;
import com.neonomics.model.pojos.AccountDataPOJO;
import com.neonomics.model.responseschema.Schemas;
import com.neonomics.utils.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Accounts implements Schemas, ConstantsRef {

	private static final String X_DEVICE_ID = "x-device-id";
	private static final String X_REDIRECT_URL = "x-redirect-url";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
	private ObjectMapper objMap = new ObjectMapper();
	private Logger logInstance = LogManager.getLogger();

	public Accounts() {
		RestAssured.baseURI = BASE_URL;
	}

	private String getConsentWebURL(String consentURI, String xDeviceID, String token, String psuID,
			String redirectURL) {
		Response resp = RestAssured.given().contentType(ContentType.JSON).header(AUTHORIZATION, "Bearer " + token)
				.header(X_DEVICE_ID, xDeviceID).header("x-psu-id", psuID).header(X_REDIRECT_URL, redirectURL).when()
				.get(consentURI).then().assertThat().statusCode(HttpStatus.SC_OK).assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(ConsentResponseSchema)).extract().response();
		
		logInstance.info("Found consent web URL [{}]",resp.jsonPath().getString("links.href[0]"));
		return resp.jsonPath().getString("links.href[0]");
	}

	private void handleConsent(String consentURL, String xDeviceID, String token, String action, String psuID,
			String redirectURL) {

		HandleConsentUI handleUI = new HandleConsentUI();

		String webURL = getConsentWebURL(consentURL, xDeviceID, token, psuID, redirectURL);
		assertNotEquals(webURL, null);
		
        logInstance.info("Consent Web URL [{}] and user action [{}]", webURL, action);
		handleUI.getConsentViaWeb(webURL, action);

	}

	public void handleBankConsent(String sessionID, String xDeviceID, String token, String action, String psuID,
			String redirectURL) {

		Response resp = RestAssured.given().header("Accept", ContentType.JSON).header(AUTHORIZATION, "Bearer " + token)
				.header(X_DEVICE_ID, xDeviceID).header("x-session-id", sessionID).header("x-psu-id", psuID)
				.header(X_REDIRECT_URL, redirectURL).when().get(Endpoints.GET_ACCOUNTS).then().extract().response();
		
		// Handling consent required
		if (resp.jsonPath().getString("errorCode").contains(String.valueOf(1426))
				&& resp.jsonPath().get("type").equals("CONSENT")) {
			
			logInstance.info("Response to check whether cosnent is needed = [{}]", resp.asString());
			logInstance.info("Bank consent is needed, therefore invoking consent handling mechenism using Web URL");
			
			assertThat(resp.body().asString(), JsonSchemaValidator.matchesJsonSchema(ConsentRequiredSchema));
			handleConsent(resp.jsonPath().getString("links.href[0]"), xDeviceID, token, action, psuID, redirectURL);
		}
	}

	public AccountDataPOJO[] getAccountsFromBank(HashMap<String, String> headData){
		
		logInstance.info("Retrieving list of accounts with session ID provided as [{}]", headData.get(X_SESSION_ID));
		AccountDataPOJO[] accountDetails = null;
		Response resp = RestAssured.given().header("Accept", ContentType.JSON).headers(headData).when().get(Endpoints.GET_ACCOUNTS)
				.then().assertThat().extract().response();

		try {
			accountDetails = objMap.readValue(resp.asString(), AccountDataPOJO[].class);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		} 
		return accountDetails;
	}

	public Boolean validateAccountBelongToBank(HashMap<String, String> headData, String accNoType,
			String accNumber) {

		Boolean isAccHosted = false;
		AccountDataPOJO[] accData = getAccountsFromBank(headData);
		
		logInstance.info("Validating that account belong to bank with session ID [{}]", headData.get(X_SESSION_ID));
		for (AccountDataPOJO account : accData) {

			if (accNoType.contains(accNoType)) {

				if (account.getIban().equals(accNumber)) {
					logInstance.info("Account number [{}] found as [{}]", accNumber, accNoType);
					isAccHosted = true;
				}

			} else if (accNoType.contains(accNoType)) {
				if (account.getBban().equals(accNumber)) {
					logInstance.info("Account number [{}] found as [{}]", accNumber, accNoType);
					isAccHosted = true;
				}
			}

		}

		return isAccHosted;
	}

}
