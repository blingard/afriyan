package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.learn.enumerations.FormationLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FormationCreateDTO {
    @NotBlank
    private String titre;
    @NotBlank
    private String description;
    @NotNull
    private CategoriesDTO categories;
    @NotBlank
    private String imageCouverture;
    @NotNull
    private FormationLevel niveau;
    @NotNull
    private Integer dureeEstimee;
    @NotNull
    private Boolean withFinalQuiz;

    public FormationCreateDTO() {
    }

    public FormationCreateDTO(String titre, CategoriesDTO categories, String description, String imageCouverture, FormationLevel niveau, Integer dureeEstimee, Boolean withFinalQuiz) {
        this.titre = titre;
        this.description = description;
        this.imageCouverture = imageCouverture;
        this.niveau = niveau;
        this.dureeEstimee = dureeEstimee;
        this.withFinalQuiz = withFinalQuiz;
        this.categories = categories;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageCouverture() {
        return imageCouverture;
    }

    public void setImageCouverture(String imageCouverture) {
        this.imageCouverture = imageCouverture;
    }

    public FormationLevel getNiveau() {
        return niveau;
    }

    public void setNiveau(FormationLevel niveau) {
        this.niveau = niveau;
    }

    public Integer getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(Integer dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }

    public Boolean getWithFinalQuiz() {
        return withFinalQuiz;
    }

    public void setWithFinalQuiz(Boolean withFinalQuiz) {
        this.withFinalQuiz = withFinalQuiz;
    }
    public CategoriesDTO getCategories() {
        return categories;
    }
    public void setCategories(CategoriesDTO categories) {
        this.categories = categories;
    }
}
