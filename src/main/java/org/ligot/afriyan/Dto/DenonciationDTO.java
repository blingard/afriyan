package org.ligot.afriyan.Dto;

import java.util.Date;

public class DenonciationDTO {

    private Long id;
    private String contenu;
    private Date dateDenonciation;
    private UtilisateurDTO utilisateur;

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateDenonciation() {
        return dateDenonciation;
    }

    public void setDateDenonciation(Date dateDenonciation) {
        this.dateDenonciation = dateDenonciation;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
