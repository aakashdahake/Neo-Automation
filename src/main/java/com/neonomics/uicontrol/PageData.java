package com.neonomics.uicontrol;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.utils.ConfigManager;

public class PageData {
	
	private static final String username = ConfigManager.getInstance( ).getString("username");
	private static final String password = ConfigManager.getInstance( ).getString("password");
	
	public PageData(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@id='username']") 
	private WebElement userTextBox;
	
	@FindBy(xpath = "//input[@id='password']") 
	private WebElement passTextBox;
	
	@FindBy(xpath = "//input[@type='submit' and @name='login']") 
	private WebElement submitLogin;
	
	@FindBy(xpath = "//input[@id = 'kc-login' and @type='submit' and @name='accept']") 
	private WebElement submitAccept;
	
	@FindBy(xpath = "//input[@id = 'kc-cancel' and @type='submit' and @name='cancel']") 
	private WebElement submitDecline;
	
	@FindBy(xpath = "//a[contains(text(),'You have successfully granted consent to')]") 
	private WebElement confirmText;
	
	public void enterUserName() {
		userTextBox.sendKeys(username);
	}
	
	public void enterPassword() {
		passTextBox.sendKeys(password);
	}
	
	public void clickLoginBtn() {
		submitLogin.click();
	}
	
	public void clickConsentAcceptBtn() {
		submitAccept.click();
	}
	
	public void clickConsentDeclineBtn() {
		submitDecline.click();
	}
	
	public void confirmConsentGranted() {
		assertEquals(confirmText.getText().contains(ConstantsRef.CONFIRMCONSENTTEXT.getConstant()), true);
	}

}
