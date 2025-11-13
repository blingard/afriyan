package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


public class UserProgressDTO {
    private UUID id;
    private UUID enrollmentId;
    private UUID moduleId;
    private String moduleTitre;
    private String status;
    private Double progressionPourcent;
    private Date dateDebut;
    private Date dateCompletion;
    private Double scoreQuiz;
    private Boolean quizPassed;
    private Date dateCreation;
    private Date dateModification;

    public UserProgressDTO() {
    }

    public UserProgressDTO(UUID id, UUID enrollmentId, UUID moduleId, String moduleTitre, String status, Double progressionPourcent, Date dateDebut, Date dateCompletion, Double scoreQuiz, Boolean quizPassed, Date dateCreation, Date dateModification) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.moduleId = moduleId;
        this.moduleTitre = moduleTitre;
        this.status = status;
        this.progressionPourcent = progressionPourcent;
        this.dateDebut = dateDebut;
        this.dateCompletion = dateCompletion;
        this.scoreQuiz = scoreQuiz;
        this.quizPassed = quizPassed;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public UUID getModuleId() {
        return moduleId;
    }

    public void setModuleId(UUID moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitre() {
        return moduleTitre;
    }

    public void setModuleTitre(String moduleTitre) {
        this.moduleTitre = moduleTitre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getProgressionPourcent() {
        return progressionPourcent;
    }

    public void setProgressionPourcent(Double progressionPourcent) {
        this.progressionPourcent = progressionPourcent;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateCompletion() {
        return dateCompletion;
    }

    public void setDateCompletion(Date dateCompletion) {
        this.dateCompletion = dateCompletion;
    }

    public Double getScoreQuiz() {
        return scoreQuiz;
    }

    public void setScoreQuiz(Double scoreQuiz) {
        this.scoreQuiz = scoreQuiz;
    }

    public Boolean getQuizPassed() {
        return quizPassed;
    }

    public void setQuizPassed(Boolean quizPassed) {
        this.quizPassed = quizPassed;
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
