
package org.ligot.afriyan.echo.dto;

import org.ligot.afriyan.Dto.UtilisateurDTO;

import java.util.List;
import java.util.UUID;

public class CommuneComityDTO {

    private UUID id;

    private LocalitiesDTO locality;

    private List<UtilisateurDTO> utilisateur;

    public CommuneComityDTO() {
    }

    public CommuneComityDTO(UUID id, LocalitiesDTO locality, List<UtilisateurDTO> utilisateur) {
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

    public LocalitiesDTO getLocality() {
        return locality;
    }

    public void setLocality(LocalitiesDTO locality) {
        this.locality = locality;
    }

    public List<UtilisateurDTO> getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(List<UtilisateurDTO> utilisateur) {
        this.utilisateur = utilisateur;
    }
}
