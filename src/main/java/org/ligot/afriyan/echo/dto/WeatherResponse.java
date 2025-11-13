package org.ligot.afriyan.echo.dto;

import java.util.List;

public class WeatherResponse {
    private List<CurrentCondition> current_condition;
    private List<NearestArea> nearest_area;
    private List<Request> request;
    private List<WeatherDTO> weather;

    public WeatherResponse() {
    }

    public WeatherResponse(List<CurrentCondition> current_condition, List<NearestArea> nearest_area, List<Request> request, List<WeatherDTO> weather) {
        this.current_condition = current_condition;
        this.nearest_area = nearest_area;
        this.request = request;
        this.weather = weather;
    }

    public List<CurrentCondition> getCurrent_condition() {
        return current_condition;
    }

    public void setCurrent_condition(List<CurrentCondition> current_condition) {
        this.current_condition = current_condition;
    }

    public List<NearestArea> getNearest_area() {
        return nearest_area;
    }

    public void setNearest_area(List<NearestArea> nearest_area) {
        this.nearest_area = nearest_area;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

    public List<WeatherDTO> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDTO> weather) {
        this.weather = weather;
    }
}
