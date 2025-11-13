package org.ligot.afriyan.echo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "country")
public class Pays {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(length = 25, nullable = false, unique = true, updatable = false)
    private String name;

    @Column(length = 5, nullable = false, unique = true, updatable = false)
    private String abbr;

    public Pays(UUID id, String name, String abbr) {
        this.id = id;
        this.name = name;
        this.abbr = abbr;
    }

    public Pays() {
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

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
