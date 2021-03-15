package com.neonomics.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4J2PropertiesConf {

	private static Logger logger = LogManager.getLogger();
	
	
		static{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hhmmss");
	        System.setProperty("current.date", dateFormat.format(new Date()));
	    }

	 
	public static void main(String[] args) {

		logger.trace("This is Trace Message.");
		logger.debug("This is Debug Message.");
		logger.info("This is Info Message.");
		logger.warn("This is Warn Message.");
		logger.error("This is Error Message.");
		logger.fatal("This is Fatal Message.");
	}
}