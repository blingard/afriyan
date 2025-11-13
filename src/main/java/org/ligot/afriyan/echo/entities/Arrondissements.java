/*
package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Arrondissements {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Departements departement;

    public Arrondissements() {
    }

    public Arrondissements(UUID id, String name, Departements departement) {
        this.id = id;
        this.name = name;
        this.departement = departement;
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

    public Departements getDepartement() {
        return departement;
    }

    public void setDepartement(Departements departement) {
        this.departement = departement;
    }
}
*/
