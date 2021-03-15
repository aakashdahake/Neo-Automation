//<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >
package com.neonomics.corelibraries;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.apache.http.HttpStatus;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.constants.Endpoints;
import com.neonomics.model.Schemas;
import com.neonomics.utils.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;


public class Authorization implements Schemas, ConstantsRef{

    private static final String BASE_URL = ConfigManager.getInstance( ).getString("base_url");
    private static final String CLIENT_ID = ConfigManager.getInstance( ).getString("client_id");
    private static final String CLIENT_SECRET = ConfigManager.getInstance( ).getString("client_secret");
  
    
    public Authorization() {
        RestAssured.baseURI = BASE_URL;
    }
    
    
    public static void send_Post_Request() {
    	
    }
    
    
    public HashMap<String,String> getAuthToken() {
    	
    	HashMap<String, String> keySet = new HashMap<>();
    	
    	Response resp =  (Response) RestAssured.given()
    			.contentType(ContentType.URLENC.withCharset("UTF-8"))
    			.formParam("grant_type", CLIENT_CREDENTIALS)
    			.formParam("client_id", CLIENT_ID )
    			.formParam("client_secret",CLIENT_SECRET )
    			.when()
    			.post(Endpoints.GET_TOKEN)
    			.then()
    			.assertThat().body(JsonSchemaValidator.matchesJsonSchema(AuthSchema))
    			.assertThat().statusCode(HttpStatus.SC_OK)
    			.extract().body();
    	
    	assertEquals(resp.jsonPath().get("access_token").toString().length(), 1191);
    	assertNotEquals(resp.jsonPath().get("access_token"), null);
    	assertNotEquals(resp.jsonPath().get("refresh_token"), null);
 
    	
    	keySet.put(ACCESS_TOKEN, resp.jsonPath().get("access_token"));
    	keySet.put(REFRESH_TOKEN, resp.jsonPath().get("refresh_token"));

    	return keySet;
        	
    }

	

    
    
}
