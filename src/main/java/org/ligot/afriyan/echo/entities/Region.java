
package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Region {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(length = 25, nullable = false, unique = true, updatable = false)
    private String name;

    @ManyToOne(optional = false)
    private Pays pays;

    public Region(UUID id, String name, Pays pays) {
        this.id = id;
        this.name = name;
        this.pays = pays;
    }

    public Region() {
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

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }
}
