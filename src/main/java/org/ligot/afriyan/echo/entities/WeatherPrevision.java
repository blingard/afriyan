package org.ligot.afriyan.echo.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
public class WeatherPrevision {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Communes communes;
    private String title;
    private String description;

    @OneToMany
    private Set<Weather> weathers;
    private boolean active;
    private LocalDate startDate;
    private LocalDate endDate;
    private String latitude;
    private String longitude;


    public WeatherPrevision() {
    }

    public WeatherPrevision(UUID id, Communes communes, String title, String description, Set<Weather> weathers, boolean active, LocalDate startDate, LocalDate endDate, String latitude, String longitude) {
        this.id = id;
        this.communes = communes;
        this.title = title;
        this.description = description;
        this.weathers = weathers;
        this.active = active;
        this.startDate = startDate;
        endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Communes getCommunes() {
        return communes;
    }

    public void setCommunes(Communes communes) {
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

    public Set<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(Set<Weather> weathers) {
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
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        endDate = endDate;
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