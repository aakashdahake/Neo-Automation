package com.neonomics.corelibraries;

import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neonomics.constants.Endpoints;
import com.neonomics.model.pojos.PaymentRequestPOJO;
import com.neonomics.model.responseschema.Schemas;
import com.neonomics.utils.Print;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Payments implements Schemas{
	

	private Logger logInstance = LogManager.getLogger();
	
	/**
	 * Initiate SEPA payment.
	 *
	 * @param payData the payload data
	 * @param headData the header data
	 * @return the hash map of status and payment Id
	 */
	public Map<String, String> initiateSEPAPayment(PaymentRequestPOJO payData, Map<String, String> headData) {

		Map<String, String> paymentStatus = new HashMap<>();
		
		try {
			Response resp =  RestAssured.given()
							.accept(ContentType.JSON)
							.contentType(ContentType.JSON)
							.headers(headData)
							.body(payData)
							.post(Endpoints.SEPA_CREDIT_PAYMENT.getConstant()).then()
							.assertThat().statusCode(HttpStatus.SC_CREATED)
							.assertThat().body(JsonSchemaValidator.matchesJsonSchema(PaymentInitiatedResponseSchema)).extract().response();
			
			Print.printResponse(resp);
			
			assertNotEquals(resp.jsonPath().get("paymentId"), null);
			assertNotEquals(resp.jsonPath().get("status"), null);
			paymentStatus.put("paymentId", resp.jsonPath().getString("paymentId"));
			paymentStatus.put("status", resp.jsonPath().getString("status"));
			
		} catch (AssertionError e) {
			e.printStackTrace();
			logInstance.error(e.getMessage());
		}
		
		return paymentStatus;
		
	}
	
	
	
	/**
	
	Methods for Domestic transfer and Scheduled domestic transfer
	
	*/
}
