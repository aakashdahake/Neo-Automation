package com.neonomics.corelibraries;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.uicontrol.PageData;
import com.neonomics.uicontrol.WebDriverManager;
import com.neonomics.utils.ConfigManager;

public class HandleConsentUI extends WebDriverManager implements ConstantsRef {
	
	private Logger logInstance = LogManager.getLogger();
	private static final String username = ConfigManager.getInstance( ).getString("username");
	private static final String password = ConfigManager.getInstance( ).getString("password");
	
	/**
	 * Gets the consent via web.
	 *
	 * @param consentURL- the consent URL
	 * @param action - Accept/Decline
	 * @return the consent via web
	 */
	public void getConsentViaWeb(String consentURL, String action) {
		
		logInstance.info("Getting consent via Web page");
		logInstance.info("Creating selenium webdriver instance");
		WebDriver driver = createDriver();
		PageData pageHandle = new PageData(driver);
		
		//Access Consent Webpage
		logInstance.info("Opening link in browser::[{}]", consentURL);
		driver.get(consentURL);
		assertEquals(driver.getTitle().contains(LOGINPAGETITLE), true);
		
		//Enter Username
		logInstance.info("Entering username in consent page as [{}]",username);
		pageHandle.enterUserName();
		
		//Enter Password
		logInstance.info("Entering password in consent page as [{}]",password);
		pageHandle.enterPassword();
		
		//Click Submit button
		logInstance.info("Clicking submit button on Web page");
		pageHandle.clickLoginBtn();
		
		//Accept consent or decline consent
		if(driver.getTitle().equals(CONFIRMATIONPAGETITLE) && action.equals(ACTION_ACCEPT)) {
			logInstance.info("Accepting consent from bank webpage");
			pageHandle.clickConsentAcceptBtn();
		}else if(driver.getTitle().equals(CONFIRMATIONPAGETITLE) && action.equals(ACTION_DECLINE)) {
			logInstance.info("Declining consent from bank webpage");
			pageHandle.clickConsentDeclineBtn();
		}
		
		//Validate consent
		logInstance.info("Confirming that user have consent");
		pageHandle.confirmConsentGranted();
		
		//Close browser
		logInstance.info("Closing browser");
		closeDriver();
		
		
	
	}
}
