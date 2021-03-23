package com.neonomics.constants;

public enum Endpoints {
		
		GET_BANKS("/ics/v3/banks"),
		GET_TOKEN("/auth/realms/sandbox/protocol/openid-connect/token"),
		CREATE_SESSION_ID("/ics/v3/session"),
		SESSION_STATUS("/ics/v3/session/{sessionId}"),
		GET_CONSENT("/ics/v3/consent/{sessionId}"),
		GET_ACCOUNTS("/ics/v3/accounts"),
		SEPA_CREDIT_PAYMENT("/ics/v3/payments/sepa-credit");
	
		public final String path;
		Endpoints(String path){
			this.path = path;
		}
		
		public String getConstant() 
	    { 
	        return this.path; 
	    } 
		
		
}
