package org.ligot.afriyan.echo.dto;

import java.util.List;

public class Hourly {
    private String DewPointC;
    private String DewPointF;
    private String FeelsLikeC;
    private String FeelsLikeF;
    private String HeatIndexC;
    private String HeatIndexF;
    private String WindChillC;
    private String WindChillF;
    private String WindGustKmph;
    private String WindGustMiles;
    private String chanceoffog;
    private String chanceoffrost;
    private String chanceofhightemp;
    private String chanceofovercast;
    private String chanceofrain;
    private String chanceofremdry;
    private String chanceofsnow;
    private String chanceofsunshine;
    private String chanceofthunder;
    private String chanceofwindy;
    private String cloudcover;
    private String diffRad;
    private String humidity;
    private String precipInches;
    private String precipMM;
    private String pressure;
    private String pressureInches;
    private String shortRad;
    private String tempC;
    private String tempF;
    private String time;
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

    public Hourly() {
    }

    public Hourly(String dewPointC, String dewPointF, String feelsLikeC, String feelsLikeF, String heatIndexC, String heatIndexF, String windChillC, String windChillF, String windGustKmph, String windGustMiles, String chanceoffog, String chanceoffrost, String chanceofhightemp, String chanceofovercast, String chanceofrain, String chanceofremdry, String chanceofsnow, String chanceofsunshine, String chanceofthunder, String chanceofwindy, String cloudcover, String diffRad, String humidity, String precipInches, String precipMM, String pressure, String pressureInches, String shortRad, String tempC, String tempF, String time, String uvIndex, String visibility, String visibilityMiles, String weatherCode, List<ValueWrapper> weatherDesc, List<ValueWrapper> weatherIconUrl, String winddir16Point, String winddirDegree, String windspeedKmph, String windspeedMiles) {
        DewPointC = dewPointC;
        DewPointF = dewPointF;
        FeelsLikeC = feelsLikeC;
        FeelsLikeF = feelsLikeF;
        HeatIndexC = heatIndexC;
        HeatIndexF = heatIndexF;
        WindChillC = windChillC;
        WindChillF = windChillF;
        WindGustKmph = windGustKmph;
        WindGustMiles = windGustMiles;
        this.chanceoffog = chanceoffog;
        this.chanceoffrost = chanceoffrost;
        this.chanceofhightemp = chanceofhightemp;
        this.chanceofovercast = chanceofovercast;
        this.chanceofrain = chanceofrain;
        this.chanceofremdry = chanceofremdry;
        this.chanceofsnow = chanceofsnow;
        this.chanceofsunshine = chanceofsunshine;
        this.chanceofthunder = chanceofthunder;
        this.chanceofwindy = chanceofwindy;
        this.cloudcover = cloudcover;
        this.diffRad = diffRad;
        this.humidity = humidity;
        this.precipInches = precipInches;
        this.precipMM = precipMM;
        this.pressure = pressure;
        this.pressureInches = pressureInches;
        this.shortRad = shortRad;
        this.tempC = tempC;
        this.tempF = tempF;
        this.time = time;
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

    public String getDewPointC() {
        return DewPointC;
    }

    public void setDewPointC(String dewPointC) {
        DewPointC = dewPointC;
    }

    public String getDewPointF() {
        return DewPointF;
    }

    public void setDewPointF(String dewPointF) {
        DewPointF = dewPointF;
    }

    public String getFeelsLikeC() {
        return FeelsLikeC;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        FeelsLikeC = feelsLikeC;
    }

    public String getFeelsLikeF() {
        return FeelsLikeF;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        FeelsLikeF = feelsLikeF;
    }

    public String getHeatIndexC() {
        return HeatIndexC;
    }

    public void setHeatIndexC(String heatIndexC) {
        HeatIndexC = heatIndexC;
    }

    public String getHeatIndexF() {
        return HeatIndexF;
    }

    public void setHeatIndexF(String heatIndexF) {
        HeatIndexF = heatIndexF;
    }

    public String getWindChillC() {
        return WindChillC;
    }

    public void setWindChillC(String windChillC) {
        WindChillC = windChillC;
    }

    public String getWindChillF() {
        return WindChillF;
    }

    public void setWindChillF(String windChillF) {
        WindChillF = windChillF;
    }

    public String getWindGustKmph() {
        return WindGustKmph;
    }

    public void setWindGustKmph(String windGustKmph) {
        WindGustKmph = windGustKmph;
    }

    public String getWindGustMiles() {
        return WindGustMiles;
    }

    public void setWindGustMiles(String windGustMiles) {
        WindGustMiles = windGustMiles;
    }

    public String getChanceoffog() {
        return chanceoffog;
    }

    public void setChanceoffog(String chanceoffog) {
        this.chanceoffog = chanceoffog;
    }

    public String getChanceoffrost() {
        return chanceoffrost;
    }

    public void setChanceoffrost(String chanceoffrost) {
        this.chanceoffrost = chanceoffrost;
    }

    public String getChanceofhightemp() {
        return chanceofhightemp;
    }

    public void setChanceofhightemp(String chanceofhightemp) {
        this.chanceofhightemp = chanceofhightemp;
    }

    public String getChanceofovercast() {
        return chanceofovercast;
    }

    public void setChanceofovercast(String chanceofovercast) {
        this.chanceofovercast = chanceofovercast;
    }

    public String getChanceofrain() {
        return chanceofrain;
    }

    public void setChanceofrain(String chanceofrain) {
        this.chanceofrain = chanceofrain;
    }

    public String getChanceofremdry() {
        return chanceofremdry;
    }

    public void setChanceofremdry(String chanceofremdry) {
        this.chanceofremdry = chanceofremdry;
    }

    public String getChanceofsnow() {
        return chanceofsnow;
    }

    public void setChanceofsnow(String chanceofsnow) {
        this.chanceofsnow = chanceofsnow;
    }

    public String getChanceofsunshine() {
        return chanceofsunshine;
    }

    public void setChanceofsunshine(String chanceofsunshine) {
        this.chanceofsunshine = chanceofsunshine;
    }

    public String getChanceofthunder() {
        return chanceofthunder;
    }

    public void setChanceofthunder(String chanceofthunder) {
        this.chanceofthunder = chanceofthunder;
    }

    public String getChanceofwindy() {
        return chanceofwindy;
    }

    public void setChanceofwindy(String chanceofwindy) {
        this.chanceofwindy = chanceofwindy;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getDiffRad() {
        return diffRad;
    }

    public void setDiffRad(String diffRad) {
        this.diffRad = diffRad;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
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

    public String getShortRad() {
        return shortRad;
    }

    public void setShortRad(String shortRad) {
        this.shortRad = shortRad;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
