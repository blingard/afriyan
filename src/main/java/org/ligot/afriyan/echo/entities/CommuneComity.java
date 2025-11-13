
package org.ligot.afriyan.echo.entities;

import jakarta.persistence.*;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.List;
import java.util.UUID;

@Entity
public class CommuneComity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Localities locality;

    @ManyToMany
    private List<Utilisateur> utilisateur;

    public CommuneComity() {
    }

    public CommuneComity(UUID id, Localities locality, List<Utilisateur> utilisateur) {
        this.id = id;
        this.locality = locality;
        this.utilisateur = utilisateur;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Localities getLocality() {
        return locality;
    }

    public void setLocality(Localities locality) {
        this.locality = locality;
    }

    public List<Utilisateur> getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(List<Utilisateur> utilisateur) {
        this.utilisateur = utilisateur;
    }
}
