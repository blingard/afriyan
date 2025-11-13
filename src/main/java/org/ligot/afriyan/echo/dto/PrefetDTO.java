
package org.ligot.afriyan.echo.dto;

import jakarta.persistence.*;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.echo.entities.Departement;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.UUID;

public class PrefetDTO {

    private UUID id;

    private DepartementDTO departement;

    private UtilisateurDTO utilisateur;
    private boolean active;

    public PrefetDTO() {
    }

    public PrefetDTO(UUID id, DepartementDTO departement, UtilisateurDTO utilisateur, boolean active) {
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

    public DepartementDTO getDepartement() {
        return departement;
    }

    public void setDepartement(DepartementDTO departement) {
        this.departement = departement;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
