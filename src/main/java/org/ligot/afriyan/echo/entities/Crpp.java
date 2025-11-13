
package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Crpp {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(updatable = false, unique = true, nullable = false)
    private Communes commune;

    @ManyToMany
    private List<Utilisateur> utilisateur;

    public Crpp() {
    }

    public Crpp(UUID id, Communes commune, List<Utilisateur> utilisateur) {
        this.id = id;
        this.commune = commune;
        this.utilisateur = utilisateur;
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

    public List<Utilisateur> getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(List<Utilisateur> utilisateur) {
        this.utilisateur = utilisateur;
    }
}
