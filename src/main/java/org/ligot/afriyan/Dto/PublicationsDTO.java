package org.ligot.afriyan.Dto;

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
}
