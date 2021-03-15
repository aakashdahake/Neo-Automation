package com.neonomics.corelibraries;

import java.util.HashMap;

import org.apache.http.HttpStatus;

import com.neonomics.constants.Endpoints;
import com.neonomics.utils.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Session extends Authorization {
	
	private static final String BASE_URL = ConfigManager.getInstance( ).getString("base_url");
	
	public Session() {
        RestAssured.baseURI = BASE_URL;
    }
	
	
	public String getSessionID(String bankID, String xDeviceID, String token) {
		
		HashMap<String, String> payload = new HashMap<String, String>(); 
		payload.put("bankId",bankID);
		
		Response resp = (Response) RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer "+ token)
				.header("x-device-id", xDeviceID)
				.body(payload)
				.when()
				.post(Endpoints.CREATE_SESSION_ID)
				.then()
				.assertThat().statusCode(HttpStatus.SC_CREATED)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchema(SessionIdSchema))
				.extract().body();
		
		return resp.jsonPath().get("sessionId");
		
	}
	
	public HashMap<String, String> getSessionStatus(String sessionID, String xDeviceID, String token) {
		
		HashMap<String, String> bankInfo = new HashMap<String, String>(); 		
		Response resp = (Response) RestAssured.given()
				.accept(ContentType.JSON)
				.pathParam("sessionId", sessionID)
				.header("Authorization", "Bearer "+ token)
				.header("x-device-id", xDeviceID)
				.get(Endpoints.SESSION_STATUS)
				.then()
				.assertThat().statusCode(HttpStatus.SC_OK)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchema(SessionStatusSchema))
				.extract().body();
		
		bankInfo.put(BANK_NAME, resp.jsonPath().get(BANK_NAME));
		bankInfo.put(BANK_ID, resp.jsonPath().get(BANK_ID));
		
		return bankInfo;
	}
	
	public Boolean terminateSession(String sessionID,String xDeviceID, String token) {
		 		
		RestAssured.given()
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer "+ token)
				.header("x-device-id", xDeviceID)
				.pathParam("sessionId", sessionID)
				.delete(Endpoints.SESSION_STATUS)
				.then()
				.assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
				.extract().body();

		return false;
	}
		
}