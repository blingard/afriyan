package org.ligot.afriyan.echo.dto;

import jakarta.persistence.*;
import org.ligot.afriyan.echo.entities.Communes;
import org.ligot.afriyan.echo.entities.LocalityType;

import java.util.UUID;

public class LocalitiesDTO {
    private UUID id;

    private String name;
    private String latitude;
    private String longitude;

    private LocalityType type;
    private Communes commune;

    public LocalitiesDTO(UUID id, String name, String latitude, String longitude, LocalityType type, Communes commune) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.commune = commune;
    }

    public LocalitiesDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalityType getType() {
        return type;
    }

    public void setType(LocalityType type) {
        this.type = type;
    }

    public Communes getCommune() {
        return commune;
    }

    public void setCommune(Communes commune) {
        this.commune = commune;
    }

    @Override
    public String toString() {
        return "LocalitiesDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", type=" + type +
                ", commune=" + commune +
                '}';
    }
}
