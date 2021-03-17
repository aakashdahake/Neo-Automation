package testRunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/java/featureFiles/Get_All_Accounts.feature", glue = "stepDefinition", plugin = {
		"pretty", "json:test-output/cucumber.json", "junit:test-output/cucumber.xml",
		"html:test-output/Accounts.html" }, publish = true, monochrome = true, dryRun = false)

public class TestRunner_Gell_All_Justo_Accounts {
	
	public static Logger logInstance = LogManager.getLogger();
	
	@BeforeClass
	public static void beforeExecution() {
		logInstance.info("********************************************************************************************************");
		logInstance.info("--- Execution Started ---");
		logInstance.info("********************************************************************************************************");
	}
	
	@AfterClass
	public static void afterExecution() {
		logInstance.info("********************************************************************************************************");
		logInstance.info("--- Execution Ends ---");
		logInstance.info("********************************************************************************************************");
	}

}
