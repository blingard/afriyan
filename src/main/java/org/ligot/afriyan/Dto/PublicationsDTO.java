package org.ligot.afriyan.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PublicationsDTO {

    private Long id;
    private String type;
    private String contenu;
    private String categorie;
    private UtilisateurDTO administrateur;
    private ServiceDTO service;

    private String reseau1;

    private String reseau2;

    private String reseau3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public UtilisateurDTO getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(UtilisateurDTO administrateur) {
        this.administrateur = administrateur;
    }

    public ServiceDTO getService() {
        return service;
    }

    public void setService(ServiceDTO service) {
        this.service = service;
    }

    public String getReseau1() {
        return reseau1;
    }

    public void setReseau1(String reseau1) {
        this.reseau1 = reseau1;
    }

    public String getReseau2() {
        return reseau2;
    }

    public void setReseau2(String reseau2) {
        this.reseau2 = reseau2;
    }

    public String getReseau3() {
        return reseau3;
    }

    public void setReseau3(String reseau3) {
        this.reseau3 = reseau3;
    }
}
