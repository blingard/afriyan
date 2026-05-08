package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class QuestionDTO {
    private UUID id;
    private String intitule;
    private String explication;
    private Integer points;
    private Integer ordre;
    private String imageUrl;
    private UUID quizId;
    private Date dateCreation;
    private Date dateModification;
    private List<QuestionOptionDTO> options;

    private boolean active;

    public QuestionDTO() {
    }

    public QuestionDTO(UUID id, String intitule, String explication, Integer points, Integer ordre, String imageUrl, UUID quizId, Date dateCreation, Date dateModification, List<QuestionOptionDTO> options, boolean active) {
        this.id = id;
        this.intitule = intitule;
        this.explication = explication;
        this.points = points;
        this.ordre = ordre;
        this.imageUrl = imageUrl;
        this.quizId = quizId;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.options = options;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getExplication() {
        return explication;
    }

    public void setExplication(String explication) {
        this.explication = explication;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getQuizId() {
        return quizId;
    }

    public void setQuizId(UUID quizId) {
        this.quizId = quizId;
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

    public List<QuestionOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionDTO> options) {
        this.options = options;
    }
}
