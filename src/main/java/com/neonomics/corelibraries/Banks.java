package com.neonomics.corelibraries;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neonomics.constants.Endpoints;
import com.neonomics.model.pojos.BankDataPOJO;
import com.neonomics.model.responseschema.Schemas;
import com.neonomics.utils.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Banks extends Authorization implements Schemas {

	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
	private BankDataPOJO[] BankDB = null;
	private Logger logInstance = LogManager.getLogger();

	public Banks() {
		RestAssured.baseURI = BASE_URL;
	}

	/**
	 * Gets the all banks.
	 *
	 * @param headData the head data
	 * @return the all banks
	 * @throws Exception the exception
	 */
	public BankDataPOJO[] getAllBanks(HashMap<String, String> headData) throws Exception {

		ObjectMapper objMap = new ObjectMapper();

		Response resp = RestAssured.given()
						.accept(ContentType.JSON)
						.headers(headData)
						.when()
						.get(Endpoints.GET_BANKS)
						.then()
						.assertThat().statusCode(HttpStatus.SC_OK)
						.assertThat().body(JsonSchemaValidator.matchesJsonSchema(BanksSchema))
						.extract().response();

		logInstance.info("All banks information recieved Response::" + resp.asString());
		try {
			BankDB = objMap.readValue(resp.asString(), BankDataPOJO[].class);
		} catch (JsonMappingException e) {
			logInstance.error(e.getMessage());
		}
		return BankDB;
	}

	/**
	 * Gets the bank details.
	 *
	 * @param bankName the bank name
	 * @param header the header
	 * @return the bank details
	 * @throws Exception the exception
	 */
	public BankDataPOJO getBankDetails(String bankName, HashMap<String, String> header) throws Exception {

		try {
			logInstance.info("Getting information about bank [{}]", bankName);
			BankDataPOJO[] allBanks = getAllBanks(header);
			for (BankDataPOJO eachBank : allBanks) {
				if (eachBank.getBankDisplayName().equalsIgnoreCase(bankName)) {
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

	/**
	 * Gets the bank ID.
	 *
	 * @param bankName the bank name
	 * @param headData the head data
	 * @return the bank ID
	 * @throws Exception the exception
	 */
	public String getBankID(String bankName, HashMap<String, String> headData) throws Exception {

		String bankId = null;

		logInstance.info("Getting bank ID for bank [{}]", bankName);
		BankDataPOJO bankInfo = getBankDetails(bankName, headData);
		assertEquals(bankInfo.getBankDisplayName(), bankName);
		if (bankInfo.getBankDisplayName().equals(bankName)) {
			logInstance.info("Found bank ID as [{}]for bank [{}]", bankInfo.getId(), bankName);
			assertNotEquals(bankInfo.getId(), null);
			bankId = bankInfo.getId();
		}

		return bankId;

	}

	/**
	 * Validate supported payment type for bank.
	 *
	 * @param bankName the bank name
	 * @param headData the head data
	 * @param paymentType the payment type
	 * @return the boolean
	 */
	public Boolean validateSupportedPaymentTypeForBank(String bankName, HashMap<String, String> headData, String paymentType) {
		Boolean isSupported = false;

		try {
			logInstance.info("Verifying that bank [{}] supports [{}] payment type", bankName, paymentType);
			BankDataPOJO bankData = getBankDetails(bankName, headData);
			if (bankData.getSupportedServices().contains(paymentType)) {
				logInstance.info("Payment method [{}] is supported by bank [{}]", paymentType, bankName);
				isSupported = true;
			}
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}

		return isSupported;
	}

}
