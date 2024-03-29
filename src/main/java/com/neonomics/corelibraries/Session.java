package com.neonomics.corelibraries;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.constants.Endpoints;
import com.neonomics.utils.ConfigManager;
import com.neonomics.utils.Print;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Session extends Authorization {

	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
	private Logger logInstance = LogManager.getLogger();
	
	public Session() {
		RestAssured.baseURI = BASE_URL;
	}

	/**
	 * Gets the session ID.
	 *
	 * @param bankID the bank ID
	 * @param headData the head data
	 * @return the session ID
	 */
	public String getSessionID(String bankID, Map<String, String> headData) {

		Map<String, String> payload = new HashMap<>();
		payload.put("bankId", bankID);

		String sessID = null;
		try {
			Response resp = RestAssured.given()
							.contentType(ContentType.JSON)
							.headers(headData)
							.body(payload)
							.when()
							.post(Endpoints.CREATE_SESSION_ID.getConstant())
							.then()
							.assertThat().statusCode(HttpStatus.SC_CREATED)
							.assertThat().body(JsonSchemaValidator.matchesJsonSchema(SessionIdSchema))
							.extract().response();
			
			Print.printResponse(resp);
			sessID = resp.jsonPath().get("sessionId");
		} catch (Exception e) {
			logInstance.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return sessID;

	}


	/**
	 * Gets the session status.
	 *
	 * @param sessionID the session ID
	 * @param headData the head data
	 * @return the session status
	 */
	public Map<String, String> getSessionStatus(String sessionID, Map<String, String> headData) {

		Map<String, String> bankInfo = new HashMap<>();
		
		try {
			Response resp = RestAssured.given()
					.accept(ContentType.JSON)
					.pathParam("sessionId", sessionID)
					.headers(headData)
					.get(Endpoints.SESSION_STATUS.getConstant())
					.then()
					.assertThat().statusCode(HttpStatus.SC_OK)
					.assertThat().body(JsonSchemaValidator.matchesJsonSchema(SessionStatusSchema))
					.extract().response();
			
			Print.printResponse(resp);
			bankInfo.put(ConstantsRef.BANK_NAME.getConstant(), resp.jsonPath().get(ConstantsRef.BANK_NAME.getConstant()));
			bankInfo.put(ConstantsRef.BANK_ID.getConstant(), resp.jsonPath().get(ConstantsRef.BANK_ID.getConstant()));
		} catch (AssertionError e) {
			logInstance.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return bankInfo;
	}

	
	/**
	 * Terminate session.
	 *
	 * @param sessionID the session ID
	 * @param headData the head data
	 */
	public void terminateSession(String sessionID, Map<String, String> headData) {

		logInstance.info("Terminating session for session ID [{}]", sessionID);
		
		try {
			RestAssured.given()
				.accept(ContentType.JSON)
				.headers(headData)
				.pathParam("sessionId", sessionID)
				.delete(Endpoints.SESSION_STATUS.getConstant())
				.then()
				.assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
			
			logInstance.info("Terminated session for session ID [{}]", sessionID);
		} catch (AssertionError e) {
			logInstance.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

	}

}
