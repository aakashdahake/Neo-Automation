
package com.neonomics.model.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "reference",
    "referenceType",
    "referenceIssuer"
})
public class RemittanceInformationStructured {

    @JsonProperty("reference")
    private String reference;
    @JsonProperty("referenceType")
    private String referenceType;
    @JsonProperty("referenceIssuer")
    private String referenceIssuer;

    @JsonProperty("reference")
    public String getReference() {
        return reference;
    }

    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    @JsonProperty("referenceType")
    public String getReferenceType() {
        return referenceType;
    }

    @JsonProperty("referenceType")
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    @JsonProperty("referenceIssuer")
    public String getReferenceIssuer() {
        return referenceIssuer;
    }

    @JsonProperty("referenceIssuer")
    public void setReferenceIssuer(String referenceIssuer) {
        this.referenceIssuer = referenceIssuer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("reference", reference).append("referenceType", referenceType).append("referenceIssuer", referenceIssuer).toString();
    }

}
