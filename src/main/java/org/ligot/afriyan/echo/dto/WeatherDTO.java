package org.ligot.afriyan.echo.dto;

import java.util.List;

public class WeatherDTO {
    private List<Astronomy> astronomy;
    private String avgtempC;
    private String avgtempF;
    private String date;
    private List<Hourly> hourly;
    private String maxtempC;
    private String maxtempF;
    private String mintempC;
    private String mintempF;
    private String sunHour;
    private String totalSnow_cm;
    private String uvIndex;

    public WeatherDTO() {
    }

    public WeatherDTO(List<Astronomy> astronomy, String avgtempC, String avgtempF, String date, List<Hourly> hourly, String maxtempC, String maxtempF, String mintempC, String mintempF, String sunHour, String totalSnow_cm, String uvIndex) {
        this.astronomy = astronomy;
        this.avgtempC = avgtempC;
        this.avgtempF = avgtempF;
        this.date = date;
        this.hourly = hourly;
        this.maxtempC = maxtempC;
        this.maxtempF = maxtempF;
        this.mintempC = mintempC;
        this.mintempF = mintempF;
        this.sunHour = sunHour;
        this.totalSnow_cm = totalSnow_cm;
        this.uvIndex = uvIndex;
    }

    public List<Astronomy> getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(List<Astronomy> astronomy) {
        this.astronomy = astronomy;
    }

    public String getAvgtempC() {
        return avgtempC;
    }

    public void setAvgtempC(String avgtempC) {
        this.avgtempC = avgtempC;
    }

    public String getAvgtempF() {
        return avgtempF;
    }

    public void setAvgtempF(String avgtempF) {
        this.avgtempF = avgtempF;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    public void setMaxtempC(String maxtempC) {
        this.maxtempC = maxtempC;
    }

    public String getMaxtempF() {
        return maxtempF;
    }

    public void setMaxtempF(String maxtempF) {
        this.maxtempF = maxtempF;
    }

    public String getMintempC() {
        return mintempC;
    }

    public void setMintempC(String mintempC) {
        this.mintempC = mintempC;
    }

    public String getMintempF() {
        return mintempF;
    }

    public void setMintempF(String mintempF) {
        this.mintempF = mintempF;
    }

    public String getSunHour() {
        return sunHour;
    }

    public void setSunHour(String sunHour) {
        this.sunHour = sunHour;
    }

    public String getTotalSnow_cm() {
        return totalSnow_cm;
    }

    public void setTotalSnow_cm(String totalSnow_cm) {
        this.totalSnow_cm = totalSnow_cm;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }
}
