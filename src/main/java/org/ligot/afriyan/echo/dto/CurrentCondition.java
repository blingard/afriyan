package org.ligot.afriyan.echo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CurrentCondition {
    @JsonProperty("FeelsLikeC")
    private String feelsLikeC;
    @JsonProperty("FeelsLikeF")
    private String feelsLikeF;
    private String cloudcover;
    private String humidity;
    private String localObsDateTime;
    private String observation_time;
    private String precipInches;
    private String precipMM;
    private String pressure;
    private String pressureInches;
    private String temp_C;
    private String temp_F;
    private String uvIndex;
    private String visibility;
    private String visibilityMiles;
    private String weatherCode;
    private List<ValueWrapper> weatherDesc;
    private List<ValueWrapper> weatherIconUrl;
    private String winddir16Point;
    private String winddirDegree;
    private String windspeedKmph;
    private String windspeedMiles;

    public CurrentCondition() {
    }

    public CurrentCondition(String feelsLikeC, String feelsLikeF, String cloudcover, String humidity, String localObsDateTime, String observation_time, String precipInches, String precipMM, String pressure, String pressureInches, String temp_C, String temp_F, String uvIndex, String visibility, String visibilityMiles, String weatherCode, List<ValueWrapper> weatherDesc, List<ValueWrapper> weatherIconUrl, String winddir16Point, String winddirDegree, String windspeedKmph, String windspeedMiles) {
        this.feelsLikeC = feelsLikeC;
        this.feelsLikeF = feelsLikeF;
        this.cloudcover = cloudcover;
        this.humidity = humidity;
        this.localObsDateTime = localObsDateTime;
        this.observation_time = observation_time;
        this.precipInches = precipInches;
        this.precipMM = precipMM;
        this.pressure = pressure;
        this.pressureInches = pressureInches;
        this.temp_C = temp_C;
        this.temp_F = temp_F;
        this.uvIndex = uvIndex;
        this.visibility = visibility;
        this.visibilityMiles = visibilityMiles;
        this.weatherCode = weatherCode;
        this.weatherDesc = weatherDesc;
        this.weatherIconUrl = weatherIconUrl;
        this.winddir16Point = winddir16Point;
        this.winddirDegree = winddirDegree;
        this.windspeedKmph = windspeedKmph;
        this.windspeedMiles = windspeedMiles;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public String getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLocalObsDateTime() {
        return localObsDateTime;
    }

    public void setLocalObsDateTime(String localObsDateTime) {
        this.localObsDateTime = localObsDateTime;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public void setObservation_time(String observation_time) {
        this.observation_time = observation_time;
    }

    public String getPrecipInches() {
        return precipInches;
    }

    public void setPrecipInches(String precipInches) {
        this.precipInches = precipInches;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getPressureInches() {
        return pressureInches;
    }

    public void setPressureInches(String pressureInches) {
        this.pressureInches = pressureInches;
    }

    public String getTemp_C() {
        return temp_C;
    }

    public void setTemp_C(String temp_C) {
        this.temp_C = temp_C;
    }

    public String getTemp_F() {
        return temp_F;
    }

    public void setTemp_F(String temp_F) {
        this.temp_F = temp_F;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVisibilityMiles() {
        return visibilityMiles;
    }

    public void setVisibilityMiles(String visibilityMiles) {
        this.visibilityMiles = visibilityMiles;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public List<ValueWrapper> getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(List<ValueWrapper> weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public List<ValueWrapper> getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(List<ValueWrapper> weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public void setWinddirDegree(String winddirDegree) {
        this.winddirDegree = winddirDegree;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }
}
