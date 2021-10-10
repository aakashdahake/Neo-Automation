package com.neonomics.utils;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.response.Response;

public class Print {

	private static Logger logInstance = LogManager.getLogger();

	/**
	 * Prints the API response.
	 *
	 * @param resp - Response
	 */
	public static void printResponse(Response resp) {

		if (resp.getBody() != null) {
			logInstance.info("Headers :: [{}]", resp.getHeaders());
			logInstance.info("Cookies :: [{}]", resp.getCookies());
			logInstance.info("Status Code :: [{}]", resp.getStatusCode());
			logInstance.info("Status Line :: [{}]", resp.getStatusLine());
			logInstance.info("Session ID :: [{}]", resp.getSessionId());
			logInstance.info("Response Time :: [{}] milliseconds", resp.getTimeIn(TimeUnit.MILLISECONDS));
			logInstance.info("Response :: [{}]", resp.print());
		}
		else {
			logInstance.error("No response body found");
		}
	}
}
