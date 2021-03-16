
package com.neonomics.model.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "debtorAccount",
    "debtorName",
    "creditorAccount",
    "creditorName",
    "remittanceInformationUnstructured",
    "instrumentedAmount",
    "currency",
    "remittanceInformationStructured",
    "endToEndIdentification",
    "paymentMetadata"
})
public class PaymentRequestPOJO {

    @JsonProperty("debtorAccount")
    private DebtorAccount debtorAccount;
    @JsonProperty("debtorName")
    private String debtorName;
    @JsonProperty("creditorAccount")
    private CreditorAccount creditorAccount;
    @JsonProperty("creditorName")
    private String creditorName;
    @JsonProperty("remittanceInformationUnstructured")
    private String remittanceInformationUnstructured;
    @JsonProperty("instrumentedAmount")
    private String instrumentedAmount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("remittanceInformationStructured")
    private RemittanceInformationStructured remittanceInformationStructured;
    @JsonProperty("endToEndIdentification")
    private String endToEndIdentification;
    @JsonProperty("paymentMetadata")
    private PaymentMetadata paymentMetadata;

    @JsonProperty("debtorAccount")
    public DebtorAccount getDebtorAccount() {
        return debtorAccount;
    }

    @JsonProperty("debtorAccount")
    public void setDebtorAccount(DebtorAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    @JsonProperty("debtorName")
    public String getDebtorName() {
        return debtorName;
    }

    @JsonProperty("debtorName")
    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    @JsonProperty("creditorAccount")
    public CreditorAccount getCreditorAccount() {
        return creditorAccount;
    }

    @JsonProperty("creditorAccount")
    public void setCreditorAccount(CreditorAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    @JsonProperty("creditorName")
    public String getCreditorName() {
        return creditorName;
    }

    @JsonProperty("creditorName")
    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    @JsonProperty("remittanceInformationUnstructured")
    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    @JsonProperty("remittanceInformationUnstructured")
    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    @JsonProperty("instrumentedAmount")
    public String getInstrumentedAmount() {
        return instrumentedAmount;
    }

    @JsonProperty("instrumentedAmount")
    public void setInstrumentedAmount(String instrumentedAmount) {
        this.instrumentedAmount = instrumentedAmount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("remittanceInformationStructured")
    public RemittanceInformationStructured getRemittanceInformationStructured() {
        return remittanceInformationStructured;
    }

    @JsonProperty("remittanceInformationStructured")
    public void setRemittanceInformationStructured(RemittanceInformationStructured remittanceInformationStructured) {
        this.remittanceInformationStructured = remittanceInformationStructured;
    }

    @JsonProperty("endToEndIdentification")
    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    @JsonProperty("endToEndIdentification")
    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
    }

    @JsonProperty("paymentMetadata")
    public PaymentMetadata getPaymentMetadata() {
        return paymentMetadata;
    }

    @JsonProperty("paymentMetadata")
    public void setPaymentMetadata(PaymentMetadata paymentMetadata) {
        this.paymentMetadata = paymentMetadata;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("debtorAccount", debtorAccount).append("debtorName", debtorName).append("creditorAccount", creditorAccount).append("creditorName", creditorName).append("remittanceInformationUnstructured", remittanceInformationUnstructured).append("instrumentedAmount", instrumentedAmount).append("currency", currency).append("remittanceInformationStructured", remittanceInformationStructured).append("endToEndIdentification", endToEndIdentification).append("paymentMetadata", paymentMetadata).toString();
    }

}
