package com.neonomics.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "iban", "bban", "accountName", "accountType", "balances" })
public class AccountDataPOJO {

	@JsonProperty("id")
	private String id;
	@JsonProperty("iban")
	private String iban;
	@JsonProperty("bban")
	private String bban;
	@JsonProperty("accountName")
	private String accountName;
	@JsonProperty("accountType")
	private String accountType;
	@JsonProperty("balances")
	private List<BalancePOJO> balances = null;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("iban")
	public String getIban() {
		return iban;
	}

	@JsonProperty("iban")
	public void setIban(String iban) {
		this.iban = iban;
	}

	@JsonProperty("bban")
	public String getBban() {
		return bban;
	}

	@JsonProperty("bban")
	public void setBban(String bban) {
		this.bban = bban;
	}

	@JsonProperty("accountName")
	public String getAccountName() {
		return accountName;
	}

	@JsonProperty("accountName")
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@JsonProperty("accountType")
	public String getAccountType() {
		return accountType;
	}

	@JsonProperty("accountType")
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", iban=" + iban + ", bban=" + bban + ", accountName=" + accountName
				+ ", accountType=" + accountType + ", balances=" + balances + "]";
	}

	@JsonProperty("balances")
	public List<BalancePOJO> getBalances() {
		return balances;
	}

	@JsonProperty("balances")
	public void setBalances(List<BalancePOJO> balances) {
		this.balances = balances;
	}

}