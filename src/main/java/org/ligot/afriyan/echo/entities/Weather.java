package org.ligot.afriyan.echo.entities;


import jakarta.persistence.*;
import org.ligot.afriyan.echo.WeatherTime;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Weather {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private WeatherTime weatherTime;
    private String temptation;
    private String skyDescription;
    private String wind;
    private String rain;
    private String humidity;
    private LocalDate date;
    private String latitude;
    private String longitude;

    @ManyToOne
    private Communes communes;
    public Weather() {
    }

    public Weather(UUID id, WeatherTime weatherTime, String temptation, String skyDescription, String wind, String rain, String humidity, LocalDate date, String latitude, String longitude, Communes communes) {
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

    public Communes getCommunes() {
        return communes;
    }

    public void setCommunes(Communes communes) {
        this.communes = communes;
    }
}