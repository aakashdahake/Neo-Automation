package com.neonomics.uicontrol;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.neonomics.constants.DriverType;
import com.neonomics.utils.ConfigManager;

 
public class WebDriverManager {
	
	private WebDriver driver;
	private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private static final String driverType = ConfigManager.getInstance( ).getString("driverType");
	private static final String driverLocation = ConfigManager.getInstance( ).getString("driverPath");
	
 
	public WebDriver getDriver() {
		if(driver == null) driver = createDriver();
		return driver;
	}
 

	/**
	 * Creates the webdriver driver instance.
	 *
	 * @return the web driver instance
	 */
	public WebDriver createDriver() {
		
		switch (driverType) {	    
        
		case DriverType.FIREFOX  : 
        	driver = new FirefoxDriver();
	    	break;
        case DriverType.CHROME : 
        	System.setProperty(CHROME_DRIVER_PROPERTY, driverLocation);
        	driver = new ChromeDriver();
    		break;
        }
		
		driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Integer.valueOf(ConfigManager.getInstance( ).getString("implicitWaitTime")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        
		return driver;
	}
 
	/**
	 * Close driver and quit driver instance
	 */
	public void closeDriver() {
		driver.close();
		driver.quit();
	}	

 
}