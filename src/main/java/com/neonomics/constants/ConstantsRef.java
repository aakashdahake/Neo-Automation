package com.neonomics.constants;


public enum ConstantsRef {
	

	LOGINPAGETITLE("Log in to"),
	CONFIRMATIONPAGETITLE("Grant Access to {0}"),
	CONFIRMCONSENTTEXT("You have successfully granted consent to"),
	ACTION_ACCEPT("Accept"),
	ACTION_DECLINE("Decline"),
	ACCESS_TOKEN("access_token"),
	AUTHORIZATION("Authorization"),
	X_DEVICE_ID("x-device-id"),
	X_SESSION_ID("x-session-id"),
	X_PSU_IP("x-psu-ip-address"),
	X_PSU_ID("x-psu-id"),
	X_REDIRECT_URL("x-redirect-url"),
	BEARER_TOKEN ("Bearer Token"),
	REFRESH_TOKEN("refresh_token"),
	BANK_NAME("bankName"),
	BANK_ID("bankId"),
	CLIENT_CREDENTIALS("client_credentials"),
	HTTP_POST("POST"),
	HTTP_GET("GET"),
	HTTP_PUT("PUT"),
	HTTP_DELETE("DELETE"),
	SESSION_CREATE("create"),
	SESSION_DELETE("delete"),
	IBAN("iban"),
	BBAN("bban"),
	SEPA_CREDIT("sepa-credit"),
	DOMESTIC_TRANSFER("domestic-transfer"),
	DOMESTIC_SCHEDULED_TRASNFER("domestic-scheduled-transfer");
	
	
	public final String constant;
	ConstantsRef(String constant) {
		this.constant = constant;
	}
	
	public String getConstant() 
    { 
        return this.constant; 
    } 
	
	


	
}
