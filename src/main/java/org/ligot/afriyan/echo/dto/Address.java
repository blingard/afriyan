package org.ligot.afriyan.echo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    private String town;
    private String county;
    private String state;

    @JsonProperty("ISO3166-2-lvl4")
    private String iso3166_2_lvl4;

    private String country;

    @JsonProperty("country_code")
    private String countryCode;
    private String city;

    public Address() {
    }

    public Address(String town, String county, String state, String iso3166_2_lvl4, String country, String countryCode, String city) {
        this.town = town;
        this.county = county;
        this.state = state;
        this.iso3166_2_lvl4 = iso3166_2_lvl4;
        this.country = country;
        this.countryCode = countryCode;
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIso3166_2_lvl4() {
        return iso3166_2_lvl4;
    }

    public void setIso3166_2_lvl4(String iso3166_2_lvl4) {
        this.iso3166_2_lvl4 = iso3166_2_lvl4;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "town='" + town + '\'' +
                ", county='" + county + '\'' +
                ", state='" + state + '\'' +
                ", iso3166_2_lvl4='" + iso3166_2_lvl4 + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
