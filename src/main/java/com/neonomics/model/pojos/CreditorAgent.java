
package com.neonomics.model.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "identification",
    "identificationType"
})
public class CreditorAgent {

    @JsonProperty("identification")
    private String identification;
    @JsonProperty("identificationType")
    private String identificationType;

    @JsonProperty("identification")
    public String getIdentification() {
        return identification;
    }

    @JsonProperty("identification")
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @JsonProperty("identificationType")
    public String getIdentificationType() {
        return identificationType;
    }

    @JsonProperty("identificationType")
    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("identification", identification).append("identificationType", identificationType).toString();
    }

}
