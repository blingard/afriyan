package org.ligot.afriyan.echo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReverseGeocodingResponse {

    @JsonProperty("place_id")
    private long placeId;

    private String licence;

    @JsonProperty("osm_type")
    private String osmType;

    @JsonProperty("osm_id")
    private long osmId;

    private String lat;
    private String lon;

    @JsonProperty("class")
    private String clazz;

    private String type;

    @JsonProperty("place_rank")
    private int placeRank;

    private double importance;

    @JsonProperty("addresstype")
    private String addressType;

    private String name;

    @JsonProperty("display_name")
    private String displayName;

    private Address address;

    private List<String> boundingbox;

    public ReverseGeocodingResponse() {
    }

    public ReverseGeocodingResponse(long placeId, String licence, String osmType, long osmId, String lat, String lon, String clazz, String type, int placeRank, double importance, String addressType, String name, String displayName, Address address, List<String> boundingbox) {
        this.placeId = placeId;
        this.licence = licence;
        this.osmType = osmType;
        this.osmId = osmId;
        this.lat = lat;
        this.lon = lon;
        this.clazz = clazz;
        this.type = type;
        this.placeRank = placeRank;
        this.importance = importance;
        this.addressType = addressType;
        this.name = name;
        this.displayName = displayName;
        this.address = address;
        this.boundingbox = boundingbox;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getOsmType() {
        return osmType;
    }

    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }

    public long getOsmId() {
        return osmId;
    }

    public void setOsmId(long osmId) {
        this.osmId = osmId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlaceRank() {
        return placeRank;
    }

    public void setPlaceRank(int placeRank) {
        this.placeRank = placeRank;
    }

    public double getImportance() {
        return importance;
    }

    public void setImportance(double importance) {
        this.importance = importance;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getBoundingbox() {
        return boundingbox;
    }

    public void setBoundingbox(List<String> boundingbox) {
        this.boundingbox = boundingbox;
    }

    @Override
    public String toString() {
        return "ReverseGeocodingResponse{" +
                "placeId=" + placeId +
                ", licence='" + licence + '\'' +
                ", osmType='" + osmType + '\'' +
                ", osmId=" + osmId +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", clazz='" + clazz + '\'' +
                ", type='" + type + '\'' +
                ", placeRank=" + placeRank +
                ", importance=" + importance +
                ", addressType='" + addressType + '\'' +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", address=" + address +
                ", boundingbox=" + boundingbox +
                '}';
    }
}