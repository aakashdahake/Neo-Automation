package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/java/featureFiles/API_Tests.feature", glue = "stepDefinition", plugin = {
		"pretty", "json:test-output/cucumber.json", "junit:test-output/cucumber.xml",
		"html:test-output/API_Tests.html"}, publish = true, monochrome = true, dryRun = false)

public class TestRunner_API_Tests {

}
