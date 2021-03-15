package stepDefinition;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.corelibraries.Authorization;
import com.neonomics.corelibraries.Session;
import com.neonomics.model.Schemas;
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
	
	Authorization auth = new Authorization();
	Session session = new Session();
	

	RequestSpecification request = RestAssured.given();

	@Given("user sets base URI and sets endpoint path as {string}")
	public void user_sets_base_uri_and_sets_endpoint_path_as(String path) {
		request.baseUri(BASE_URL);
		request.basePath(path);
	}

	@Then("user sets {string} as {string} as header")
	public void user_sets_as_as_header(String key, String value) {

		if (key.contains(AUTHORIZATION) && value.equals(BEARER_TOKEN)) {
			accessToken = auth.getAuthToken().get(ACCESS_TOKEN);
			value = "Bearer " + accessToken;
		}

		requestHeaders.putIfAbsent(key, value);

	}

	@Then("user sets body params for {string} as {string}")
	public void user_sets_body_params_for_as(String key, String value) {
		requestParams.put(key, value);
	}

	@When("user makes {string} request to endpoint")
	public void user_makes_request_to_endpoint(String requestType) {

		try {

			if (isBody) {
				Gson jsonBody = new Gson();
				String bodyContent = jsonBody.toJson(jsonAsMap);
				request.body(bodyContent);
				isBody = false;
			}
			request.headers(requestHeaders);
			request.formParams(requestParams);

			response = request.request(requestType);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Then("user validates status code as {int}")
	public void user_validates_status_code_as(int code) {
		assertEquals(response.getStatusCode(), code);
	}

	@Then("user validates that API should work for {string} as {string}")
	public void user_validates_that_api_should_work_for_as(String area, String work) {

		boolean result;
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
		default:
			System.out.println("Please provide proper API area type");
		}

		JsonSchemaValidator st = JsonSchemaValidator.matchesJsonSchema(schema);

		if (response.body().asString() != "") {
			result = st.matches(response.body().asString());
			assertNotEquals(result, work);
		}

	}

	@Then("user puts body content as {string} as {string}")
	public void user_puts_body_content_as_as(String key, String value) {
		try {
			isBody = true;
			jsonAsMap.put(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Then("user creates session ID with device id as {string} and bankId as {string} and puts it in endpoint URI")
	public void user_creates_session_id_with_device_id_as_and_bank_id_as_and_puts_it_in_endpoint_uri(String xDeviceId,
			String bankId) {
		try {
			String sessID = session.getSessionID(bankId, xDeviceId, accessToken);
			request.pathParam("sessionId", sessID);
			request.log().all();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
