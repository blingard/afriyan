package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public class QuizDTO {
    private UUID id;
    private String titre;
    private String description;
    private String type;
    private Integer scoreMinimum;
    private Integer dureeLimite;
    private Integer nombreTentativesMax;
    private UUID moduleId;
    private UUID formationId;
    private Date dateCreation;
    private Date dateModification;
    private List<QuestionDTO> questions;
    private Integer nombreQuestions;
    private Integer pointsTotaux;

    public QuizDTO() {
    }

    public QuizDTO(UUID id, String titre, String description, String type, Integer scoreMinimum, Integer dureeLimite, Integer nombreTentativesMax, UUID moduleId, UUID formationId, Date dateCreation, Date dateModification, List<QuestionDTO> questions, Integer nombreQuestions, Integer pointsTotaux) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.scoreMinimum = scoreMinimum;
        this.dureeLimite = dureeLimite;
        this.nombreTentativesMax = nombreTentativesMax;
        this.moduleId = moduleId;
        this.formationId = formationId;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.questions = questions;
        this.nombreQuestions = nombreQuestions;
        this.pointsTotaux = pointsTotaux;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getScoreMinimum() {
        return scoreMinimum;
    }

    public void setScoreMinimum(Integer scoreMinimum) {
        this.scoreMinimum = scoreMinimum;
    }

    public Integer getDureeLimite() {
        return dureeLimite;
    }

    public void setDureeLimite(Integer dureeLimite) {
        this.dureeLimite = dureeLimite;
    }

    public Integer getNombreTentativesMax() {
        return nombreTentativesMax;
    }

    public void setNombreTentativesMax(Integer nombreTentativesMax) {
        this.nombreTentativesMax = nombreTentativesMax;
    }

    public UUID getModuleId() {
        return moduleId;
    }

    public void setModuleId(UUID moduleId) {
        this.moduleId = moduleId;
    }

    public UUID getFormationId() {
        return formationId;
    }

    public void setFormationId(UUID formationId) {
        this.formationId = formationId;
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

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Integer getNombreQuestions() {
        return nombreQuestions;
    }

    public void setNombreQuestions(Integer nombreQuestions) {
        this.nombreQuestions = nombreQuestions;
    }

    public Integer getPointsTotaux() {
        return pointsTotaux;
    }

    public void setPointsTotaux(Integer pointsTotaux) {
        this.pointsTotaux = pointsTotaux;
    }
}
