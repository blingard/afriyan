package org.ligot.afriyan.partenaire.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PartenaireCreateDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'image est obligatoire")
    private String imageBase64;

    private String description;

    @NotNull(message = "Le statut est obligatoire")
    private Boolean statut;

    @NotNull(message = "Le champ publish est obligatoire")
    private Boolean publish;

    public PartenaireCreateDTO() {
    }

    public PartenaireCreateDTO(String nom, String imageBase64, String description, Boolean statut, Boolean publish) {
        this.nom = nom;
        this.imageBase64 = imageBase64;
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
    public String getImageBase64() {
        return imageBase64;
    }
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
