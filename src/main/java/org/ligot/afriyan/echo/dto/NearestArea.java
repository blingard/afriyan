package org.ligot.afriyan.echo.dto;

import java.util.List;

public class NearestArea {
    private List<ValueWrapper> areaName;
    private List<ValueWrapper> country;
    private String latitude;
    private String longitude;
    private String population;
    private List<ValueWrapper> region;
    private List<ValueWrapper> weatherUrl;

    public NearestArea() {
    }

    public NearestArea(List<ValueWrapper> areaName, List<ValueWrapper> country, String latitude, String longitude, String population, List<ValueWrapper> region, List<ValueWrapper> weatherUrl) {
        this.areaName = areaName;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.region = region;
        this.weatherUrl = weatherUrl;
    }

    public List<ValueWrapper> getAreaName() {
        return areaName;
    }

    public void setAreaName(List<ValueWrapper> areaName) {
        this.areaName = areaName;
    }

    public List<ValueWrapper> getCountry() {
        return country;
    }

    public void setCountry(List<ValueWrapper> country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public List<ValueWrapper> getRegion() {
        return region;
    }

    public void setRegion(List<ValueWrapper> region) {
        this.region = region;
    }

    public List<ValueWrapper> getWeatherUrl() {
        return weatherUrl;
    }

    public void setWeatherUrl(List<ValueWrapper> weatherUrl) {
        this.weatherUrl = weatherUrl;
    }
}
