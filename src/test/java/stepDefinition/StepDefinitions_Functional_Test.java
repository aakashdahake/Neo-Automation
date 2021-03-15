package stepDefinition;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neonomics.constants.ConstantsRef;
import com.neonomics.corelibraries.Accounts;
import com.neonomics.corelibraries.Authorization;
import com.neonomics.corelibraries.Banks;
import com.neonomics.corelibraries.Session;
import com.neonomics.model.AccountDataPOJO;
import com.neonomics.model.BankDataPOJO;
import com.neonomics.utils.ConfigManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDefinitions_Functional_Test implements ConstantsRef {

	private static final String XDEVICEID = ConfigManager.getInstance().getString("x-device-id");

	Authorization auth = new Authorization();
	Banks bank = new Banks();
	Accounts account = new Accounts();
	Session session = new Session();

	private Logger logInstance = LogManager.getLogger();

	private HashMap<String, String> token;
	private String bankId;
	private String sessID;

	@Given("user gets authetication token for Neonomics platform")
	public void user_gets_authetication_token_for_neonomics_platform() {

		try {
			token = auth.getAuthToken();
			logInstance.info("Getting authentication token set");
			logInstance.info("Access token and Refresh token::" + token);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user retrieves bank ID for {string}")
	public void user_retrieves_bank_id_for(String bankName) {

		try {
			bankId = bank.getBankID(bankName, XDEVICEID, token.get(ACCESS_TOKEN));
			logInstance.info("Received Bank ID [{}] for bank [{}] with devide ID [{}].", bankId, bankName, XDEVICEID);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user {string} session for {string}")
	public void user_session_for(String action, String bankName) {

		try {

			switch (action) {
			case SESSION_CREATE:
				logInstance.info("Creating session ID for bank [{}] when Device ID [{}] with token [{}]", bankName,
						XDEVICEID);
				sessID = session.getSessionID(bankId, XDEVICEID, token.get(ACCESS_TOKEN));
				logInstance.info("Recieved session ID as [{}]", sessID);
				break;
			case SESSION_DELETE:
				logInstance.info("Deleting session for bank [{}]", bankName);
				session.terminateSession(sessID, XDEVICEID, token.get(ACCESS_TOKEN));
				logInstance.info("Deleted session ID as [{}]", sessID);
				break;
			default:
				logInstance.error("Please provide proper action, it should be [create] or [delete]!!");

			}

		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}

	}

	@Then("user validates session status for {string}")
	public void user_validates_session_status_for(String bankName) {
		try {
			logInstance.info("Getting details for [{}] bank", bankName);

			HashMap<String, String> bankDetails = session.getSessionStatus(sessID, XDEVICEID, token.get(ACCESS_TOKEN));
			logInstance.info("Got bank details to validate current session [{}]", bankDetails);

			assertEquals(bankDetails.get(BANK_NAME), bankName);
			assertEquals(bankDetails.get(BANK_ID), bankId);

		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user checks for {string} consent and {string} if required provided redirect URL as {string}")
	public void user_checks_for_consent_and_if_required_provided_redirect_url_as(String bankName, String action,
			String redirectURL) {
		try {

			String psuID = "";

			logInstance.info(
					"Getting [{}] bank details to seek consent, where user [{}] consent provided Device ID [{}] and redirect URL [{}]",
					bankName, action, redirectURL);
			BankDataPOJO bankPOJO = bank.getBankDetails(bankName, XDEVICEID, token.get(ACCESS_TOKEN));

			logInstance.info("Received bank details for bank [{}] as [{}]", bankName, bankPOJO.toString());
			logInstance.info("Checking whether PSU ID is required for bank [{}]", bankName);
			Boolean isPSUIDrequired = bankPOJO.getPersonalIdentificationRequired();
			if (isPSUIDrequired) {
				logInstance.info("Bank [{}] requires PSU ID", bankName);

				// PSU ID from source, currently using temperory hard-coded one
				psuID = "AQf3CuTr6q1uM0PtiztUpyTmlhAiu6JWEpUlSn0A/qUa7xQILOolKu4=";
			}

			logInstance.info("Handling bank consent");
			account.handleBankConsent(sessID, XDEVICEID, token.get(ACCESS_TOKEN), action, psuID, redirectURL);

		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user fetches all accounts from {string}")
	public void user_fetches_all_accounts_from(String bankName) {
		try {
			
			AccountDataPOJO[] accData = account.getAccountsFromBank(sessID, XDEVICEID, token.get(ACCESS_TOKEN));

			for (AccountDataPOJO acc : accData) {
				logInstance.info("Account details from [{}] are = [{}]", bankName, acc.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
