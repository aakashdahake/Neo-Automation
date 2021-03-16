package com.neonomics.corelibraries;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neonomics.constants.Endpoints;
import com.neonomics.utils.ConfigManager;

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

	public String getSessionID(String bankID, HashMap<String, String> headData) {

		HashMap<String, String> payload = new HashMap<String, String>();
		payload.put("bankId", bankID);

		Response resp = RestAssured.given().contentType(ContentType.JSON).headers(headData).body(payload).when()
				.post(Endpoints.CREATE_SESSION_ID).then().assertThat().statusCode(HttpStatus.SC_CREATED).assertThat().body(JsonSchemaValidator.matchesJsonSchema(SessionIdSchema)).extract()
				.response();

		logInstance.info("Response recieved for getting session ID ::" + resp.asString());
		return resp.jsonPath().get("sessionId");

	}

	public HashMap<String, String> getSessionStatus(String sessionID, HashMap<String, String> headData) {

		HashMap<String, String> bankInfo = new HashMap<String, String>();
		Response resp = RestAssured.given().accept(ContentType.JSON).pathParam("sessionId", sessionID).headers(headData)
				.get(Endpoints.SESSION_STATUS).then().assertThat().statusCode(HttpStatus.SC_OK).assertThat().body(JsonSchemaValidator.matchesJsonSchema(SessionStatusSchema)).extract()
				.response();

		logInstance.info("Response recieved for getting current session status ::" + resp.asString());
		bankInfo.put(BANK_NAME, resp.jsonPath().get(BANK_NAME));
		bankInfo.put(BANK_ID, resp.jsonPath().get(BANK_ID));

		return bankInfo;
	}

	public void terminateSession(String sessionID,HashMap<String, String> headData ) {

		logInstance.info("Terminating session for session ID [{}]", sessionID);
		RestAssured.given().accept(ContentType.JSON).headers(headData).pathParam("sessionId", sessionID)
				.delete(Endpoints.SESSION_STATUS).then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT);

		logInstance.info("Terminated session for session ID [{}]", sessionID);

	}

}
