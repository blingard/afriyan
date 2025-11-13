
package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.UUID;

@Entity
@Table(name = "maire")
public class Maire {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "commune_id")
    private Communes commune;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateur utilisateur;
    private boolean active;

    public Maire() {
    }

    public Maire(UUID id, Communes commune, Utilisateur utilisateur, boolean active) {
        this.id = id;
        this.commune = commune;
        this.utilisateur = utilisateur;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Communes getCommune() {
        return commune;
    }

    public void setCommune(Communes commune) {
        this.commune = commune;
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
