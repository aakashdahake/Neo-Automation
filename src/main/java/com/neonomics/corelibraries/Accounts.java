package com.neonomics.corelibraries;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neonomics.constants.Endpoints;
import com.neonomics.model.AccountDataPOJO;
import com.neonomics.model.Schemas;
import com.neonomics.utils.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Accounts implements Schemas {

	private static final String X_DEVICE_ID = "x-device-id";
	private static final String X_REDIRECT_URL = "x-redirect-url";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
	private ObjectMapper objMap = new ObjectMapper();
	
	public Accounts() {
		RestAssured.baseURI = BASE_URL;
	}

	private String getConsentWebURL(String consentURI, String xDeviceID, String token, String psuID,
			String redirectURL) {
		Response resp = (Response) RestAssured.given().contentType(ContentType.JSON)
				.header(AUTHORIZATION, "Bearer " + token).header(X_DEVICE_ID, xDeviceID).header("x-psu-id", psuID)
				.header(X_REDIRECT_URL, redirectURL).when().get(consentURI).then().assertThat()
				.statusCode(HttpStatus.SC_OK).assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(ConsentResponseSchema)).extract().body();
		return resp.jsonPath().getString("links.href[0]");
	}

	private void handleConsent(String consentURL, String xDeviceID, String token, String action, String psuID,
			String redirectURL) {

		HandleConsentUI handleUI = new HandleConsentUI();

		String webURL = getConsentWebURL(consentURL, xDeviceID, token, psuID, redirectURL);
		assertNotEquals(webURL, null);

		handleUI.getConsentViaWeb(webURL, action);

	}

	public void handleBankConsent(String sessionID, String xDeviceID, String token, String action, String psuID,
			String redirectURL) {

		Response resp;

		resp = (Response) RestAssured.given().header("Accept", ContentType.JSON)
				.header(AUTHORIZATION, "Bearer " + token).header(X_DEVICE_ID, xDeviceID)
				.header("x-session-id", sessionID).header("x-psu-id", psuID).header(X_REDIRECT_URL, redirectURL)
				.when().get(Endpoints.GET_ACCOUNTS).then().extract().body();

		// Handling consent if required
		if (resp.jsonPath().getString("errorCode").contains(String.valueOf(1426))
				&& resp.jsonPath().get("type").equals("CONSENT")) {
			assertThat(resp.body().asString(), JsonSchemaValidator.matchesJsonSchema(ConsentRequiredSchema));
			handleConsent(resp.jsonPath().getString("links.href[0]"), xDeviceID, token, action, psuID, redirectURL);
		}
	}

	public AccountDataPOJO[] getAccountsFromBank(String sessionID,String xDeviceID, String token) {
		
		AccountDataPOJO[] accountDetails =null;
		Response resp;
		resp = (Response) RestAssured.given().header("Accept", ContentType.JSON)
				.header(AUTHORIZATION, "Bearer " + token).header(X_DEVICE_ID, xDeviceID)
				.header("x-session-id", sessionID).when().get(Endpoints.GET_ACCOUNTS).then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(AccountDataSchema)).extract().body();

		
		try {
			accountDetails = objMap.readValue(resp.asString(), AccountDataPOJO[].class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return accountDetails;
	}

}
