
package com.neonomics.model.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "creditorAddress",
    "creditorAgent",
    "paymentContextCode",
    "merchantCategoryCode",
    "merchantCustomerIdentification"
})
public class PaymentMetadata {

    @JsonProperty("creditorAddress")
    private CreditorAddress creditorAddress;
    @JsonProperty("creditorAgent")
    private CreditorAgent creditorAgent;
    @JsonProperty("paymentContextCode")
    private String paymentContextCode;
    @JsonProperty("merchantCategoryCode")
    private String merchantCategoryCode;
    @JsonProperty("merchantCustomerIdentification")
    private String merchantCustomerIdentification;

    @JsonProperty("creditorAddress")
    public CreditorAddress getCreditorAddress() {
        return creditorAddress;
    }

    @JsonProperty("creditorAddress")
    public void setCreditorAddress(CreditorAddress creditorAddress) {
        this.creditorAddress = creditorAddress;
    }

    @JsonProperty("creditorAgent")
    public CreditorAgent getCreditorAgent() {
        return creditorAgent;
    }

    @JsonProperty("creditorAgent")
    public void setCreditorAgent(CreditorAgent creditorAgent) {
        this.creditorAgent = creditorAgent;
    }

    @JsonProperty("paymentContextCode")
    public String getPaymentContextCode() {
        return paymentContextCode;
    }

    @JsonProperty("paymentContextCode")
    public void setPaymentContextCode(String paymentContextCode) {
        this.paymentContextCode = paymentContextCode;
    }

    @JsonProperty("merchantCategoryCode")
    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    @JsonProperty("merchantCategoryCode")
    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    @JsonProperty("merchantCustomerIdentification")
    public String getMerchantCustomerIdentification() {
        return merchantCustomerIdentification;
    }

    @JsonProperty("merchantCustomerIdentification")
    public void setMerchantCustomerIdentification(String merchantCustomerIdentification) {
        this.merchantCustomerIdentification = merchantCustomerIdentification;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("creditorAddress", creditorAddress).append("creditorAgent", creditorAgent).append("paymentContextCode", paymentContextCode).append("merchantCategoryCode", merchantCategoryCode).append("merchantCustomerIdentification", merchantCustomerIdentification).toString();
    }

}
