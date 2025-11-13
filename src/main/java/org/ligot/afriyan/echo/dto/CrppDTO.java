
package org.ligot.afriyan.echo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.echo.entities.Communes;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.List;
import java.util.UUID;


public class CrppDTO {

    private UUID id;

    private CommunesDTO commune;

    private List<UtilisateurDTO> utilisateur;

    public CrppDTO() {
    }

    public CrppDTO(UUID id, CommunesDTO commune, List<UtilisateurDTO> utilisateur) {
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

    public CommunesDTO getCommune() {
        return commune;
    }

    public void setCommune(CommunesDTO commune) {
        this.commune = commune;
    }

    public List<UtilisateurDTO> getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(List<UtilisateurDTO> utilisateur) {
        this.utilisateur = utilisateur;
    }
}
