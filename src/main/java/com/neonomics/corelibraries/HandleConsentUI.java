package com.neonomics.corelibraries;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.uicontrol.PageData;
import com.neonomics.uicontrol.WebDriverManager;

public class HandleConsentUI extends WebDriverManager implements ConstantsRef {

	public void getConsentViaWeb(String consentURL, String action) {
		
		WebDriver driver = createDriver();
		PageData pageHandle = new PageData(driver);
		
		//Access Consent Webpage
		driver.get(consentURL);
		assertEquals(driver.getTitle(), LOGINPAGETITLE);
		
		//Enter Username
		pageHandle.enterUserName();
		
		//Enter Password
		pageHandle.enterPassword();
		
		//Click Submit button
		pageHandle.clickLoginBtn();
		
		//Accept consent or decline consent
		if(driver.getTitle().equals(CONFIRMATIONPAGETITLE) && action.equals(ACTION_ACCEPT)) {
			pageHandle.clickConsentAcceptBtn();
		}else if(driver.getTitle().equals(CONFIRMATIONPAGETITLE) && action.equals(ACTION_DECLINE)) {
			pageHandle.clickConsentDeclineBtn();
		}
		
		//Validate consent
		pageHandle.confirmConsentGranted();
		
		//Close browser
		closeDriver();
		
		
	
	}
}
