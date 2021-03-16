package com.neonomics.model.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"countryCode",
"bankingGroupName",
"personalIdentificationRequired",
"id",
"bankDisplayName",
"supportedServices",
"bic",
"bankOfficialName",
"status"
})
public class BankDataPOJO {

@JsonProperty("countryCode")
private String countryCode;
@JsonProperty("bankingGroupName")
private String bankingGroupName;
@JsonProperty("personalIdentificationRequired")
private Boolean personalIdentificationRequired;
@JsonProperty("id")
private String id;
@JsonProperty("bankDisplayName")
private String bankDisplayName;
@JsonProperty("supportedServices")
private List<String> supportedServices = null;
@JsonProperty("bic")
private String bic;
@JsonProperty("bankOfficialName")
private String bankOfficialName;
@JsonProperty("status")
private String status;

@JsonProperty("countryCode")
public String getCountryCode() {
return countryCode;
}

@JsonProperty("countryCode")
public void setCountryCode(String countryCode) {
this.countryCode = countryCode;
}

@JsonProperty("bankingGroupName")
public String getBankingGroupName() {
return bankingGroupName;
}

@JsonProperty("bankingGroupName")
public void setBankingGroupName(String bankingGroupName) {
this.bankingGroupName = bankingGroupName;
}

@JsonProperty("personalIdentificationRequired")
public Boolean getPersonalIdentificationRequired() {
return personalIdentificationRequired;
}

@JsonProperty("personalIdentificationRequired")
public void setPersonalIdentificationRequired(Boolean personalIdentificationRequired) {
this.personalIdentificationRequired = personalIdentificationRequired;
}

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("bankDisplayName")
public String getBankDisplayName() {
return bankDisplayName;
}

@JsonProperty("bankDisplayName")
public void setBankDisplayName(String bankDisplayName) {
this.bankDisplayName = bankDisplayName;
}

@JsonProperty("supportedServices")
public List<String> getSupportedServices() {
return supportedServices;
}

@JsonProperty("supportedServices")
public void setSupportedServices(List<String> supportedServices) {
this.supportedServices = supportedServices;
}

@JsonProperty("bic")
public String getBic() {
return bic;
}

@JsonProperty("bic")
public void setBic(String bic) {
this.bic = bic;
}

@JsonProperty("bankOfficialName")
public String getBankOfficialName() {
return bankOfficialName;
}

@JsonProperty("bankOfficialName")
public void setBankOfficialName(String bankOfficialName) {
this.bankOfficialName = bankOfficialName;
}

@JsonProperty("status")
public String getStatus() {
return status;
}

@JsonProperty("status")
public void setStatus(String status) {
this.status = status;
}

public String toString() {
	return "BankData [countryCode=" + countryCode + ", bankingGroupName=" + bankingGroupName
			+ ", personalIdentificationRequired=" + personalIdentificationRequired + ", id=" + id
			+ ", bankDisplayName=" + bankDisplayName + ", supportedServices=" + supportedServices + ", bic=" + bic
			+ ", bankOfficialName=" + bankOfficialName + ", status=" + status + "]";
}

}