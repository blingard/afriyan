package org.ligot.afriyan.learn.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class QuestionOptionDTO {
    private UUID id;
    private String texte;
    private Boolean isCorrect;
    private Integer ordre;
    private UUID questionId;
    private boolean active;

    public QuestionOptionDTO() {
    }

    public QuestionOptionDTO(UUID id, String texte, Boolean isCorrect, Integer ordre, UUID questionId, boolean active) {
        this.id = id;
        this.texte = texte;
        this.isCorrect = isCorrect;
        this.ordre = ordre;
        this.questionId = questionId;
        this.active = active;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
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

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }
}
