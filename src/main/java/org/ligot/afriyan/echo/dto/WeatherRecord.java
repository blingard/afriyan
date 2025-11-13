package org.ligot.afriyan.echo.dto;


import org.ligot.afriyan.echo.WeatherTime;

import java.time.LocalDate;
import java.util.UUID;

public class WeatherRecord {
    private UUID id;
    private WeatherTime weatherTime;
    private String temptation;
    private String skyDescription;
    private String wind;
    private String rain;
    private String humidity;
    private LocalDate date;

    private String latitude;
    private String longitude;

    private CommunesDTO communes;
    public WeatherRecord() {
    }

    public WeatherRecord(UUID id, WeatherTime weatherTime, String temptation, String skyDescription, String wind, String rain, String humidity, LocalDate date, String latitude, String longitude, CommunesDTO communes) {
        this.id = id;
        this.weatherTime = weatherTime;
        this.temptation = temptation;
        this.skyDescription = skyDescription;
        this.wind = wind;
        this.rain = rain;
        this.humidity = humidity;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.communes = communes;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public WeatherTime getWeatherTime() {
        return weatherTime;
    }

    public void setWeatherTime(WeatherTime weatherTime) {
        this.weatherTime = weatherTime;
    }

    public String getTemptation() {
        return temptation;
    }

    public void setTemptation(String temptation) {
        this.temptation = temptation;
    }

    public String getSkyDescription() {
        return skyDescription;
    }

    public void setSkyDescription(String skyDescription) {
        this.skyDescription = skyDescription;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CommunesDTO getCommunes() {
        return communes;
    }

    public void setCommunes(CommunesDTO communes) {
        this.communes = communes;
    }
}