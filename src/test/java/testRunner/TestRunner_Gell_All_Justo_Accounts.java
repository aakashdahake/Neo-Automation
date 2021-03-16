package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/java/featureFiles/Get_All_Accounts.feature", glue = "stepDefinition", tags = "@test", plugin = {
		"pretty", "json:test-output/cucumber.json", "junit:test-output/cucumber.xml",
		"html:test-output/Accounts.html" }, publish = true, monochrome = true, dryRun = false)

public class TestRunner_Gell_All_Justo_Accounts {


}
