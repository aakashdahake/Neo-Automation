package testRunner;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/java/featureFiles/Get_All_Accounts.feature", 
		glue = "stepDefinition", 
		plugin = { "pretty", "json:test-output/cucumber.json","testng:test-output/cucumber.xml", "html:test-output/Accounts.html" }, 
		publish = false, 
		monochrome = true, 
		dryRun = false
		//tags = "@test3"
		)

@Test
public class TestRunner_Gell_All_Justo_Accounts extends AbstractTestNGCucumberTests {

}
