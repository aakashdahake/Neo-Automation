package com.neonomics.corelibraries;

import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.constants.Endpoints;
import com.neonomics.model.responseschema.Schemas;
import com.neonomics.utils.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Authorization implements Schemas {

	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
	private static final String CLIENT_ID = ConfigManager.getInstance().getString("client_id");
	private static final String CLIENT_SECRET = ConfigManager.getInstance().getString("client_secret");
	private Logger logInstance = LogManager.getLogger();

	/**
	 * Instantiates a new authorization.
	 */
	public Authorization() {
		RestAssured.baseURI = BASE_URL;
	}


	/**
	 * Gets the auth token.
	 *
	 * @return the auth token
	 */
	public Map<String, String> getAuthToken() {

		Map<String, String> keySet = new HashMap<>();
		
		try {
			
		
			Response resp = RestAssured.given()
							.contentType(ContentType.URLENC.withCharset("UTF-8"))
							.formParam("grant_type", ConstantsRef.CLIENT_CREDENTIALS.getConstant())
							.formParam("client_id", CLIENT_ID)
							.formParam("client_secret", CLIENT_SECRET).when()
							.post(Endpoints.GET_TOKEN.getConstant())
							.then()
							.assertThat().body(JsonSchemaValidator.matchesJsonSchema(AuthSchema))
							.assertThat().statusCode(HttpStatus.SC_OK)
							.extract().response();
		
		
			logInstance.info("Headers :: [{}]",resp.getHeaders());
			logInstance.info("Cookies :: [{}]",resp.getCookies());
			logInstance.info("Status Code :: [{}]",resp.getStatusCode());
			logInstance.info("Status Line :: [{}]",resp.getStatusLine());
			logInstance.info("Session ID :: [{}]",resp.getSessionId());
			logInstance.info("Response Time :: [{}] milliseconds",resp.getTimeIn(TimeUnit.MILLISECONDS));
			logInstance.info("Response :: [{}]",resp.print());

			assertNotEquals(resp.jsonPath().get("access_token"), null);
			assertNotEquals(resp.jsonPath().get("refresh_token"), null);

			keySet.put(ConstantsRef.ACCESS_TOKEN.getConstant(), resp.jsonPath().get("access_token"));
			keySet.put(ConstantsRef.REFRESH_TOKEN.getConstant(), resp.jsonPath().get("refresh_token"));
		
		} catch (AssertionError e) {
			e.printStackTrace();
			logInstance.error(e.getMessage());
		}
		
		return keySet;

	}

	///// Refresh token library - to be implemented

}
