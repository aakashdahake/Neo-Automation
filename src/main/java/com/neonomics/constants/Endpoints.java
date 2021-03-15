package com.neonomics.constants;

public class Endpoints {

		public static final String GET_TOKEN  = "/auth/realms/sandbox/protocol/openid-connect/token";
		public static final String GET_BANKS  = "/ics/v3/banks";
		public static final String GET_BANK_BY_ID ="/ics/v3/banks/{id}";
		public static final String GET_BANK_BY_NAME ="/ics/v3/banks?name=<name>";
		public static final String CREATE_SESSION_ID ="/ics/v3/session";
		public static final String SESSION_STATUS = "/ics/v3/session/{sessionId}";
		public static final String GET_CONSENT = "/ics/v3/consent/{sessionId}";
		public static final String GET_ACCOUNTS = "/ics/v3/accounts";
}
