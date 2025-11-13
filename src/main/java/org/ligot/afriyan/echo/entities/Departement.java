
package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
public class Departement {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 25, nullable = false, unique = true, updatable = false)
    private String name;

    @ManyToOne(optional = false)
    private Region region;

    public Departement(UUID id, String name, Region region) {
        this.id = id;
        this.name = name;
        this.region = region;
    }

    public Departement() {
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
