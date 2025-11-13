package org.ligot.afriyan.partenaire.dto;

import java.util.Date;
import java.util.UUID;

public class PartenaireDTO {

    private UUID id;
    private String nom;
    private String description;
    private Boolean statut;
    private Boolean publish;
    private Date dateCreation;
    private Date dateModification;

    public PartenaireDTO() {
    }

    public PartenaireDTO(UUID id, String nom, String description, Boolean statut, Boolean publish, Date dateCreation, Date dateModification) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.statut = statut;
        this.publish = publish;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
}
