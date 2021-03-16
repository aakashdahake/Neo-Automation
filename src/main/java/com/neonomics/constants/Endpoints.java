package com.neonomics.constants;

public class Endpoints {
		
		/** The Constant GET_TOKEN. */
		public static final String GET_TOKEN  = "/auth/realms/sandbox/protocol/openid-connect/token";
		
		/** The Constant GET_BANKS. */
		public static final String GET_BANKS  = "/ics/v3/banks";
		
		/** The Constant GET_BANK_BY_ID. */
		public static final String GET_BANK_BY_ID ="/ics/v3/banks/{id}";
		
		/** The Constant GET_BANK_BY_NAME. */
		public static final String GET_BANK_BY_NAME ="/ics/v3/banks?name=<name>";
		
		/** The Constant CREATE_SESSION_ID. */
		public static final String CREATE_SESSION_ID ="/ics/v3/session";
		
		/** The Constant SESSION_STATUS. */
		public static final String SESSION_STATUS = "/ics/v3/session/{sessionId}";
		
		/** The Constant GET_CONSENT. */
		public static final String GET_CONSENT = "/ics/v3/consent/{sessionId}";
		
		/** The Constant GET_ACCOUNTS. */
		public static final String GET_ACCOUNTS = "/ics/v3/accounts";
		
		/** The Constant SEPA_CREDIT_PAYMENT. */
		public static final String SEPA_CREDIT_PAYMENT = "/ics/v3/payments/sepa-credit";
		
		
}
