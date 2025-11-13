
package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
public class Communes {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 25, nullable = false)
    private String name;

    @Column
    private String latitude;
    @Column
    private String longitude;

    @ManyToOne(optional = false)
    private Departement departement;

    public Communes(UUID id, String name, Departement departement, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.departement = departement;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Communes() {
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

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
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
