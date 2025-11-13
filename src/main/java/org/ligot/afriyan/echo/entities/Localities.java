package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "locality")
public class Localities {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String longitude;

    @Enumerated(EnumType.STRING)
    private LocalityType type;
    @ManyToOne(optional = false)
    private Communes commune;

    public Localities(UUID id, String name, String latitude, String longitude, LocalityType type, Communes commune) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.commune = commune;
    }

    public Localities() {
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
        return "Localities{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", type=" + type +
                ", commune=" + commune +
                '}';
    }
}
