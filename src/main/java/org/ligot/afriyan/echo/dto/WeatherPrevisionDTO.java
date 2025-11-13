package org.ligot.afriyan.echo.dto;

import org.ligot.afriyan.echo.entities.Weather;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class WeatherPrevisionDTO{
    private UUID id;

    private CommunesDTO communes;
    private String title;
    private String description;

    private Set<WeatherRecord> weathers;
    private boolean active;

    private LocalDate startDate;
    private LocalDate EndDate;
    private String latitude;
    private String longitude;

    public WeatherPrevisionDTO() {
    }

    public WeatherPrevisionDTO(UUID id, CommunesDTO communes, String title, String description, Set<WeatherRecord> weathers, boolean active, LocalDate startDate, LocalDate endDate, String latitude, String longitude) {
        this.id = id;
        this.communes = communes;
        this.title = title;
        this.description = description;
        this.weathers = weathers;
        this.active = active;
        this.startDate = startDate;
        EndDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CommunesDTO getCommunes() {
        return communes;
    }

    public void setCommunes(CommunesDTO communes) {
        this.communes = communes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<WeatherRecord> getWeathers() {
        return weathers;
    }

    public void setWeathers(Set<WeatherRecord> weathers) {
        this.weathers = weathers;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
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
}
