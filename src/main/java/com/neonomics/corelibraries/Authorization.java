package com.neonomics.corelibraries;

import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;

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

public class Authorization implements Schemas, ConstantsRef {

	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
	private static final String CLIENT_ID = ConfigManager.getInstance().getString("client_id");
	private static final String CLIENT_SECRET = ConfigManager.getInstance().getString("client_secret");
	private Logger logInstance = LogManager.getLogger();

	public Authorization() {
		RestAssured.baseURI = BASE_URL;
	}

	public static void send_Post_Request() {

	}

	public HashMap<String, String> getAuthToken() {

		HashMap<String, String> keySet = new HashMap<>();

		Response resp = RestAssured.given().contentType(ContentType.URLENC.withCharset("UTF-8"))
				.formParam("grant_type", CLIENT_CREDENTIALS).formParam("client_id", CLIENT_ID)
				.formParam("client_secret", CLIENT_SECRET).when().post(Endpoints.GET_TOKEN).then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(AuthSchema)).assertThat().statusCode(HttpStatus.SC_OK)
				.extract().response();

		logInstance.info("Response recieved for auth API ::" + resp.asString());

		assertNotEquals(resp.jsonPath().get("access_token"), null);
		assertNotEquals(resp.jsonPath().get("refresh_token"), null);

		keySet.put(ACCESS_TOKEN, resp.jsonPath().get("access_token"));
		keySet.put(REFRESH_TOKEN, resp.jsonPath().get("refresh_token"));

		return keySet;

	}

	///// Refresh token library - to be implemented

}
