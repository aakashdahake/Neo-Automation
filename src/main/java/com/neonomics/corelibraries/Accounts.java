package com.neonomics.corelibraries;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neonomics.constants.ConstantsRef;
import com.neonomics.constants.Endpoints;
import com.neonomics.model.pojos.AccountDataPOJO;
import com.neonomics.model.responseschema.Schemas;
import com.neonomics.utils.ConfigManager;
import com.neonomics.utils.Print;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;


public class Accounts implements Schemas {


	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");

	
	private ObjectMapper objMap = new ObjectMapper();
	private Logger logInstance = LogManager.getLogger();

	/**
	 * Instantiates a new accounts.
	 */
	public Accounts() {
		RestAssured.baseURI = BASE_URL;
	}

	/**
	 * Gets the consent web URL.
	 *
	 * @param consentURI the consent URI
	 * @param headData the head data
	 * @return the consent web URL
	 */
	private String getConsentWebURL(String consentURI, Map<String, String> headData) {
		
		String URL=null;
		
		try {
			Response resp = RestAssured.given()
							.contentType(ContentType.JSON)
							.headers(headData)
							.when()
							.get(consentURI)
							.then()
							.assertThat().statusCode(HttpStatus.SC_OK)
							.assertThat().body(JsonSchemaValidator.matchesJsonSchema(ConsentResponseSchema))
							.extract().response();
			
			Print.printResponse(resp);
			logInstance.info("Found consent web URL [{}]",resp.jsonPath().getString("links.href[0]"));
			
			URL = resp.jsonPath().getString("links.href[0]");
		} catch (AssertionError e) {
			e.printStackTrace();
			logInstance.error(e.getMessage());
		}
		
		return URL;
	}

	/**
	 * Handle consent.
	 *
	 * @param consentURL the consent URL
	 * @param headData the head data
	 * @param action the action
	 */
	private void handleConsent(String consentURL, Map<String, String> headData, String action) {

		HandleConsentUI handleUI = new HandleConsentUI();

		String webURL = getConsentWebURL(consentURL, headData);
		assertNotEquals(webURL, null);
		
        logInstance.info("Consent Web URL [{}] and user action [{}]", webURL, action);
		handleUI.getConsentViaWeb(webURL, action);

	}

	/**
	 * Handle bank consent.
	 *
	 * @param headData the head data
	 * @param action the action
	 */
	public void handleBankConsent(Map<String, String> headData, String action) {

		try {
			Response resp = RestAssured.given()
						.header("Accept", ContentType.JSON)
						.headers(headData)
						.when()
						.get(Endpoints.GET_ACCOUNTS.getConstant())
						.then()
						.extract().response();
		
			Print.printResponse(resp);
		
			// Handling consent required
			if (resp.jsonPath().getString("errorCode").contains(String.valueOf(1426))
				&& resp.jsonPath().get("type").equals("CONSENT")) {
			
				logInstance.info("Response to check whether consent is needed = [{}]", resp.asString());
				logInstance.info("Bank consent is needed, therefore invoking consent handling mechanism using Web URL");
			
				assertThat(resp.body().asString(), JsonSchemaValidator.matchesJsonSchema(ConsentRequiredSchema));
				handleConsent(resp.jsonPath().getString("links.href[0]"), headData, action);
			}
		
		} catch (AssertionError e) {
			e.printStackTrace();
			logInstance.error(e.getMessage());
		}
	}

	/**
	 * Gets the accounts from bank.
	 *
	 * @param headData the head data
	 * @return the accounts from bank
	 */
	public AccountDataPOJO[] getAccountsFromBank(Map<String, String> headData){
		
		AccountDataPOJO[] accountDetails = null;
		try {
			
			logInstance.info("Retrieving list of accounts with session ID provided as [{}]", headData.get(ConstantsRef.X_DEVICE_ID.getConstant()));
			
			Response resp = RestAssured.given()
							.header("Accept", ContentType.JSON)
							.headers(headData)
							.when()
							.get(Endpoints.GET_ACCOUNTS.getConstant())
							.then()
							.extract().response();

			Print.printResponse(resp);
			
			accountDetails = objMap.readValue(resp.asString(), AccountDataPOJO[].class);
		
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		} 
		return accountDetails;
	}

	/**
	 * Validate account belong to bank.
	 *
	 * @param headData the head data
	 * @param accNoType the acc no type
	 * @param accNumber the acc number
	 * @return the boolean
	 */
	public Boolean validateAccountBelongToBank(Map<String, String> headData, String accNoType,String accNumber) {

		Boolean isAccHosted = false;
		AccountDataPOJO[] accData = getAccountsFromBank(headData);
		
		logInstance.info("Validating that account belong to bank with session ID [{}]", headData.get(ConstantsRef.X_SESSION_ID.getConstant()));
		for (AccountDataPOJO account : accData) {
			if ((accNoType.equalsIgnoreCase(ConstantsRef.IBAN.getConstant()) && account.getIban().equals(accNumber)) || (accNoType.equalsIgnoreCase(ConstantsRef.BBAN.getConstant()) && account.getBban().equals(accNumber))) {	
				logInstance.info("Account number [{}] found as [{}]", accNumber, accNoType);
				isAccHosted = true;
			} 
		}

		return isAccHosted;
	}

}
