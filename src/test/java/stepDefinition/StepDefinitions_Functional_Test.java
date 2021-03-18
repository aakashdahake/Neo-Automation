package stepDefinition;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neonomics.constants.ConstantsRef;
import com.neonomics.corelibraries.Accounts;
import com.neonomics.corelibraries.Authorization;
import com.neonomics.corelibraries.Banks;
import com.neonomics.corelibraries.Payments;
import com.neonomics.corelibraries.Session;
import com.neonomics.model.pojos.AccountDataPOJO;
import com.neonomics.model.pojos.BankDataPOJO;
import com.neonomics.model.pojos.CreditorAccount;
import com.neonomics.model.pojos.DebtorAccount;
import com.neonomics.model.pojos.PaymentRequestPOJO;
import com.neonomics.utils.ConfigManager;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDefinitions_Functional_Test implements ConstantsRef {

	private static final String XDEVICEID = ConfigManager.getInstance().getString(X_DEVICE_ID);
	private static final String XPSUID = ConfigManager.getInstance().getString(X_PSU_ID);

	Authorization auth = new Authorization();
	Banks bank = new Banks();
	Accounts account = new Accounts();
	Session session = new Session();
	Payments payment = new Payments();

	private Logger logInstance = LogManager.getLogger();

	private HashMap<String, String> token;
	private HashMap<String, String> sessionIDS = new HashMap<String, String>();
	private HashMap<String, String> bankIDS = new HashMap<String, String>();
	private HashMap<String, String> header = new HashMap<String, String>();
	private HashMap<String, String> paymentResponseData = new HashMap<String, String>();
	Scenario scenario;

	@Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        logInstance.info("********************************************************************************************************");
		logInstance.info("--- Execution Started :::: {}",scenario.getName());
		logInstance.info("********************************************************************************************************");

    }
	
	@Given("user gets authentication token for Neonomics platform")
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
			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);

			String bankId = bank.getBankID(bankName, header);
			logInstance.info("Received Bank ID [{}] for bank [{}] with provided device ID [{}].", bankId, bankName, XDEVICEID);
			bankIDS.put(bankName, bankId);
		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user {string} session for {string}")
	public void user_session_for(String action, String bankName) {

		try {
			
			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);
			
			switch (action) {
			case SESSION_CREATE:
				logInstance.info("Creating session ID for bank [{}] when Device ID [{}] with token [{}]", bankName, XDEVICEID, token.get(ACCESS_TOKEN));
				String sessId = session.getSessionID(bankIDS.get(bankName), header);
				logInstance.info("Recieved session ID as [{}]", sessId);
				sessionIDS.put(bankName, sessId);
				break;
				
			case SESSION_DELETE:
				logInstance.info("Deleting session for bank [{}]", bankName);
				session.terminateSession( sessionIDS.get(bankName), header);
				logInstance.info("Deleted session ID as [{}]", sessionIDS.get(bankName));
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
			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);
			
			logInstance.info("Getting details for [{}] bank", bankName);

			HashMap<String, String> bankDetails = session.getSessionStatus(sessionIDS.get(bankName), header);
			logInstance.info("Got bank details to validate current session [{}]", bankDetails);

			assertEquals(bankDetails.get(BANK_NAME), bankName);
			assertEquals(bankDetails.get(BANK_ID), bankIDS.get(bankName));

		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user checks for {string} consent and {string} if required provided redirect URL as {string}")
	public void user_checks_for_consent_and_if_required_provided_redirect_url_as(String bankName, String action, String redirectURL) {
		try {

			String psuID = "";
			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);

			logInstance.info("Getting [{}] bank details to seek consent, where user [{}] consent provided Device ID [{}] and redirect URL [{}]", bankName, action, XDEVICEID, redirectURL);
			BankDataPOJO bankPOJO = bank.getBankDetails(bankName, header);

			logInstance.info("Received bank details for bank [{}] as [{}]", bankName, bankPOJO.toString());
			logInstance.info("Checking whether PSU ID is required for bank [{}]", bankName);

			Boolean isPSUIDrequired = bankPOJO.getPersonalIdentificationRequired();
			if (isPSUIDrequired) {
				logInstance.info("Bank [{}] requires PSU ID, provided PSU ID [{}]", bankName, XPSUID);
				psuID = XPSUID;
			}
			
			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);
			header.put(X_REDIRECT_URL, redirectURL);
			header.put(X_SESSION_ID, sessionIDS.get(bankName));
			header.put(X_PSU_ID, psuID);
			
			logInstance.info("Handling bank consent");
			account.handleBankConsent(header, action);

		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user fetches all accounts from {string}")
	public void user_fetches_all_accounts_from(String bankName) {
		try {

			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);
			header.put(X_SESSION_ID, sessionIDS.get(bankName));

			AccountDataPOJO[] accData = account.getAccountsFromBank(header);

			for (AccountDataPOJO acc : accData) {
				logInstance.info("Account details from [{}] are = [{}]", bankName, acc.toString());
			}

		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}
	}

	@Then("user transfer amount of {string} {string} from {string} bank account {string} {string} to {string} bank account {string} with {string} payment method")
	public void user_transfer_amount_of_from_bank_account_to_bank_account_with_payment_method(String amt, String curr, String senderBnk, String accNoType, String senderbanNo, String tgtBnk,
			String tgtbanNo, String payMthd) {

		CreditorAccount credAccData;
		PaymentRequestPOJO payReqBody;
		ObjectMapper objMap = new ObjectMapper();
		credAccData = new CreditorAccount();
		DebtorAccount debAccData = new DebtorAccount();
		header.clear();

		try {

			// Verify that sender bank and receiver bank have account with provided iban and
			// supports payment method
			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);
			Boolean sendBnkSupport = bank.validateSupportedPaymentTypeForBank(senderBnk, header, payMthd);
			logInstance.info("Sender bank [{}] have support for payment type [{}]::[{}]", senderBnk, payMthd, sendBnkSupport);

			// Verify that sender bank and receiver bank have account with provided iban and
			// supports payment method
			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);
			Boolean recBnkSupport = bank.validateSupportedPaymentTypeForBank(tgtBnk, header, payMthd);
			logInstance.info("Sender bank [{}] have support for payment type [{}]::[{}]", tgtBnk, payMthd, recBnkSupport);

			// Validating that respective bank hosts respective account
			// Setting headers
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);
			header.put(X_SESSION_ID, sessionIDS.get(senderBnk));

			logInstance.info("Validating that provided account number is present on sender's bank [{}] with session ID [{}]", senderBnk, sessionIDS.get(senderBnk));
			Boolean isSenderAccHosted = account.validateAccountBelongToBank(header, accNoType, senderbanNo);

			// Setting headers for next request
			header.clear();
			header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
			header.put(X_DEVICE_ID, XDEVICEID);
			header.put(X_SESSION_ID, sessionIDS.get(tgtBnk));

			logInstance.info("Validating that provided account number is present on receiver's bank [{}] with session ID [{}]", tgtBnk, sessionIDS.get(tgtBnk));
			Boolean isReceiverAccHosted = account.validateAccountBelongToBank(header, accNoType, tgtbanNo);

			if (sendBnkSupport && recBnkSupport && isSenderAccHosted && isReceiverAccHosted) {

				File file = new File("test-data-files/paymentInitiateData.json");
				payReqBody = objMap.readValue(file, PaymentRequestPOJO.class);

				// Set Header
				header.clear();
				header.put(AUTHORIZATION, "Bearer " + token.get(ACCESS_TOKEN));
				header.put(X_DEVICE_ID, XDEVICEID);
				header.put(X_SESSION_ID, sessionIDS.get(senderBnk));
				header.put(X_PSU_ID, XPSUID);

				// Set Body
				debAccData.setIban(senderbanNo);
				credAccData.setIban(tgtbanNo);
				payReqBody.setCreditorName("Aakash");
				payReqBody.setDebtorName("Johan");
				payReqBody.setDebtorAccount(debAccData);
				payReqBody.setCreditorAccount(credAccData);
				payReqBody.setInstrumentedAmount(amt);
				payReqBody.setCurrency(curr);

				paymentResponseData = payment.initiateSEPAPayment(payReqBody, header);
				logInstance.info("Payment initiation Response data [{}]", paymentResponseData);
				
			}

		} catch (Exception e) {
			logInstance.error(e.getMessage());
		}

	}

	@Then("user validates payment request is initiated")
	public void user_validates_payment_request_is_initiated() {
		// To be implemented
	}

	@Then("user authorizes the the payment and obtains authorization URL")
	public void user_authorizes_the_the_payment_and_obtains_authorization_url() {
		// To be implemented
	}

	@Then("user authorizes process using authorization URL")
	public void user_authorizes_process_using_authorization_url() {
		// To be implemented
	}
}
