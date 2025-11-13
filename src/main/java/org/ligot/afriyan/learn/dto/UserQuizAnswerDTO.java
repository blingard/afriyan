package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


public class UserQuizAnswerDTO {
    private UUID id;
    private UUID attemptId;
    private UUID questionId;
    private String questionIntitule;
    private UUID selectedOptionId;
    private String selectedOptionTexte;
    private Boolean isCorrect;
    private Integer pointsObtenus;
    private Date dateReponse;
    private String explication;

    public UserQuizAnswerDTO() {
    }

    public UserQuizAnswerDTO(UUID id, UUID attemptId, UUID questionId, String questionIntitule, UUID selectedOptionId, String selectedOptionTexte, Boolean isCorrect, Integer pointsObtenus, Date dateReponse, String explication) {
        this.id = id;
        this.attemptId = attemptId;
        this.questionId = questionId;
        this.questionIntitule = questionIntitule;
        this.selectedOptionId = selectedOptionId;
        this.selectedOptionTexte = selectedOptionTexte;
        this.isCorrect = isCorrect;
        this.pointsObtenus = pointsObtenus;
        this.dateReponse = dateReponse;
        this.explication = explication;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(UUID attemptId) {
        this.attemptId = attemptId;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getQuestionIntitule() {
        return questionIntitule;
    }

    public void setQuestionIntitule(String questionIntitule) {
        this.questionIntitule = questionIntitule;
    }

    public UUID getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(UUID selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public String getSelectedOptionTexte() {
        return selectedOptionTexte;
    }

    public void setSelectedOptionTexte(String selectedOptionTexte) {
        this.selectedOptionTexte = selectedOptionTexte;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        this.isCorrect = correct;
    }

    public Integer getPointsObtenus() {
        return pointsObtenus;
    }

    public void setPointsObtenus(Integer pointsObtenus) {
        this.pointsObtenus = pointsObtenus;
    }

    public Date getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }

    public String getExplication() {
        return explication;
    }

    public void setExplication(String explication) {
        this.explication = explication;
    }
}
