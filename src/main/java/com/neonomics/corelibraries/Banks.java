package com.neonomics.corelibraries;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neonomics.constants.Endpoints;
import com.neonomics.model.BankDataPOJO;
import com.neonomics.model.Schemas;
import com.neonomics.utils.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Banks extends Authorization implements Schemas{
	
	private static final String BASE_URL = ConfigManager.getInstance( ).getString("base_url");
	private BankDataPOJO[] BankDB=null;
	private Logger logInstance = LogManager.getLogger();
	
	public Banks() {
        RestAssured.baseURI = BASE_URL;
    }
	
	public BankDataPOJO[] getAllBanks(String xDeviceID, String token) throws Exception {
		
		ObjectMapper objMap = new ObjectMapper();
	
		Response resp = (Response) RestAssured.given()
				.header("Accept",ContentType.JSON)
				.header("Authorization", "Bearer "+token)
				.header("x-device-id", xDeviceID)
				.when()
				.get(Endpoints.GET_BANKS)
				.then()
				.assertThat().statusCode(HttpStatus.SC_OK)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchema(BanksSchema))
				.extract().body();
		
		logInstance.info("All banks information recieved Response::" + resp.asString());
		try {	
			BankDB = objMap.readValue(resp.asString(), BankDataPOJO[].class);
		} catch (JsonMappingException e) {
			logInstance.error(e.getMessage());
		}
		return BankDB;
	}
	
	
	public BankDataPOJO getBankDetails(String bankName, String xDeviceID, String token) throws Exception {
		
		try {
			logInstance.info("Getting information about bank [{}]", bankName);
			
			BankDataPOJO[] allBanks = getAllBanks(xDeviceID, token);
			for(BankDataPOJO eachBank : allBanks) {
				if(eachBank.getBankDisplayName().equalsIgnoreCase(bankName)){
					logInstance.info("Found bank [{}] from all bank collection", eachBank.toString());
					return eachBank;
				}
				}
			logInstance.error("Did not find the requested bank info for [{}]", bankName);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
		return null;
	}
	
	
	public String getBankID(String bankName, String xDeviceID, String token) throws Exception {
		
		logInstance.info("Getting b=bank ID for bank [{}]", bankName);
		BankDataPOJO bankInfo = getBankDetails(bankName,xDeviceID, token);
		assertEquals(bankInfo.getBankDisplayName(), bankName);
		if(bankInfo.getBankDisplayName().equals(bankName)) {
			logInstance.info("Found bank ID as [{}]",bankInfo.getId());
			assertNotEquals(bankInfo.getId(), null);
			return bankInfo.getId();
		}
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
}
