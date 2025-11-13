package org.ligot.afriyan.partenaire.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PartenaireCreateDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String description;

    @NotNull(message = "Le statut est obligatoire")
    private Boolean statut;

    @NotNull(message = "Le champ publish est obligatoire")
    private Boolean publish;

    public PartenaireCreateDTO() {
    }

    public PartenaireCreateDTO(String nom, String description, Boolean statut, Boolean publish) {
        this.nom = nom;
        this.description = description;
        this.statut = statut;
        this.publish = publish;
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
}
