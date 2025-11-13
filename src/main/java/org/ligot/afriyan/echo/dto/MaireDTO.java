
package org.ligot.afriyan.echo.dto;

import org.ligot.afriyan.Dto.UtilisateurDTO;

import java.util.UUID;

public class MaireDTO {

    private UUID id;
    private CommunesDTO commune;
    private UtilisateurDTO utilisateur;
    private boolean active;

    public MaireDTO() {
    }

    public MaireDTO(UUID id, CommunesDTO commune, UtilisateurDTO utilisateur, boolean active) {
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

    public CommunesDTO getCommune() {
        return commune;
    }

    public void setCommune(CommunesDTO commune) {
        this.commune = commune;
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
