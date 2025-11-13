
package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.UUID;

@Entity
@Table(name = "prefet")
public class Prefet {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateur utilisateur;

    private boolean active;

    public Prefet() {
    }

    public Prefet(UUID id, Departement departement, Utilisateur utilisateur, boolean active) {
        this.id = id;
        this.departement = departement;
        this.utilisateur = utilisateur;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
