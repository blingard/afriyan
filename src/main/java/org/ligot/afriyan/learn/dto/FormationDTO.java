package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.learn.enumerations.FormationLevel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class FormationDTO {
    private UUID id;
    private String titre;
    private String description;
    private String imageCouverture;
    private FormationLevel niveau;
    private CategoriesDTO categories;
    private Integer dureeEstimee;
    private String status;
    private Boolean withFinalQuiz;
    private Date dateCreation;
    private Date dateModification;
    private Long createdBy;
    private Integer nombreModules;
    private Integer nombreInscrits;
    private List<ModuleDTO> modules = new ArrayList<>();

    public FormationDTO() {
    }

    public FormationDTO(UUID id, String titre, String description, String imageCouverture, FormationLevel niveau, Integer dureeEstimee, String status, Boolean withFinalQuiz, Date dateCreation, Date dateModification, Long createdBy, Integer nombreModules, Integer nombreInscrits, List<ModuleDTO> modules, CategoriesDTO categories) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.imageCouverture = imageCouverture;
        this.niveau = niveau;
        this.dureeEstimee = dureeEstimee;
        this.status = status;
        this.withFinalQuiz = withFinalQuiz;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.createdBy = createdBy;
        this.nombreModules = nombreModules;
        this.nombreInscrits = nombreInscrits;
        this.modules = modules;
        this.categories = categories;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getWithFinalQuiz() {
        return withFinalQuiz;
    }

    public void setWithFinalQuiz(Boolean withFinalQuiz) {
        this.withFinalQuiz = withFinalQuiz;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getNombreModules() {
        return nombreModules;
    }

    public void setNombreModules(Integer nombreModules) {
        this.nombreModules = nombreModules;
    }

    public Integer getNombreInscrits() {
        return nombreInscrits;
    }

    public void setNombreInscrits(Integer nombreInscrits) {
        this.nombreInscrits = nombreInscrits;
    }

    public List<ModuleDTO> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDTO> modules) {
        this.modules = modules;
    }
    public CategoriesDTO getCategories() {
        return categories;
    }
    public void setCategories(CategoriesDTO categories) {
        this.categories = categories;
    }
}
