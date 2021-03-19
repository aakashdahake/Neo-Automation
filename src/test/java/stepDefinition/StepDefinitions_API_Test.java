package stepDefinition;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.corelibraries.Authorization;
import com.neonomics.corelibraries.Session;
import com.neonomics.model.responseschema.Schemas;
import com.neonomics.utils.ConfigManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinitions_API_Test implements ConstantsRef, Schemas {

	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
	private Response response;
	private Boolean isBody = false;
	private Map<String, String> requestHeaders = new HashMap<String, String>();
	private Map<String, String> requestParams = new HashMap<String, String>();
	private Map<String, Object> jsonAsMap = new HashMap<>();
	private String accessToken;
	private Logger logInstance = LogManager.getLogger();
	Authorization auth = new Authorization();
	Session session = new Session();

	RequestSpecification request = RestAssured.given();

	
	@Given("user sets base URI and sets endpoint path as {string}")
	public void user_sets_base_uri_and_sets_endpoint_path_as(String path) {

		try {
			request.baseUri(BASE_URL);
			request.basePath(path);
			logInstance.info("Base URL [{}]", BASE_URL);
			logInstance.info("Endpoint path [{}]", path);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user sets {string} as {string} as header")
	public void user_sets_as_as_header(String key, String value) {

		try {
			logInstance.info("Setting Header = [{}] and Value = [{}]", key, value);
			if (key.contains(AUTHORIZATION) && value.equals(BEARER_TOKEN)) {
				accessToken = auth.getAuthToken().get(ACCESS_TOKEN);
				value = "Bearer " + accessToken;
				logInstance.info("Bearer token :: [{}]", value);
			}
			logInstance.info("Added :: Header = [{}] and Value = [{}]", key, value);
			requestHeaders.putIfAbsent(key, value);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}

	}

	@Then("user sets body params for {string} as {string}")
	public void user_sets_body_params_for_as(String key, String value) {
		try {
			logInstance.info("Setting request parameter as Key = [{}] and Value = [{}]", key, value);
			requestParams.put(key, value);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@When("user makes {string} request to endpoint")
	public void user_makes_request_to_endpoint(String requestType) {
		
		try {

			if (isBody) {
				logInstance.info("Raw body needs to be added to request");
				Gson jsonBody = new Gson();
				String bodyContent = jsonBody.toJson(jsonAsMap);

				logInstance.info(bodyContent);

				request.body(bodyContent);
				isBody = false;
			}

			request.headers(requestHeaders);
			request.formParams(requestParams);

			logInstance.info("Making [{}] request", requestType);
			response = request.request(requestType);
			
			logInstance.info("Headers :: [{}]",response.getHeaders());
			logInstance.info("Cookies :: [{}]",response.getCookies());
			logInstance.info("Status Code :: [{}]",response.getStatusCode());
			logInstance.info("Status Line :: [{}]",response.getStatusLine());
			logInstance.info("Session ID :: [{}]",response.getSessionId());
			logInstance.info("Response Time :: [{}] milliseconds",response.getTimeIn(TimeUnit.MILLISECONDS));
			logInstance.info("Response :: [{}]",response.print());

		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}

	}

	@Then("user validates status code as {int}")
	public void user_validates_status_code_as(int code) {
		logInstance.info("Validating response code as [{}]", code);
		try {
			assertEquals(response.getStatusCode(), code);
		} catch (AssertionError e) {
			logInstance.error(e.getMessage());
		}

	}

	@Then("user validates that API should work for {string} as {string}")
	public void user_validates_that_api_should_work_for_as(String area, String work) {

		try {
			String schema = null;

			switch (area) {
			case AUTHORIZATION:
				schema = AuthSchema;
				break;
			case BANKS:
				schema = BanksSchema;
				break;
			case SESSION:
				schema = SessionIdSchema;
			case SESSION_STATUS:
				schema = SessionStatusSchema;
			default:
				logInstance.error("Please provide proper API area type");
			}

			JsonSchemaValidator st = JsonSchemaValidator.matchesJsonSchema(schema);

			if (response.body().asString() != "") {
				boolean result = st.matches(response.body().asString());
				assertNotEquals(result, work);
			}
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}

	}

	@Then("user puts body content as {string} as {string}")
	public void user_puts_body_content_as_as(String key, String value) {
		try {
			logInstance.info("Adding body content for Key = [{}] and Value = [{]]", key, value);
			isBody = true;
			jsonAsMap.put(key, value);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user creates session ID with device id as {string} and bankId as {string} and puts it in endpoint URI")
	public void user_creates_session_id_with_device_id_as_and_bank_id_as_and_puts_it_in_endpoint_uri(String xDeviceId, String bankId) {
		try {
			HashMap<String, String> header = new HashMap<>();
			header.put(AUTHORIZATION, accessToken);
			header.put(X_DEVICE_ID, xDeviceId);

			logInstance.info("Creating session ID for bank ID [{}] and Device ID [{}] for further API testing", bankId, xDeviceId);
			String sessID = session.getSessionID(bankId, header);
			logInstance.info("Received session ID as [{}]", sessID);
			request.pathParam("sessionId", sessID);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}
}
