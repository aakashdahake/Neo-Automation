
package com.neonomics.model.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "streetName",
    "buildingNumber",
    "postalCode",
    "city",
    "country"
})
public class CreditorAddress {

    @JsonProperty("streetName")
    private String streetName;
    @JsonProperty("buildingNumber")
    private String buildingNumber;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;

    @JsonProperty("streetName")
    public String getStreetName() {
        return streetName;
    }

    @JsonProperty("streetName")
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @JsonProperty("buildingNumber")
    public String getBuildingNumber() {
        return buildingNumber;
    }

    @JsonProperty("buildingNumber")
    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("streetName", streetName).append("buildingNumber", buildingNumber).append("postalCode", postalCode).append("city", city).append("country", country).toString();
    }

}
