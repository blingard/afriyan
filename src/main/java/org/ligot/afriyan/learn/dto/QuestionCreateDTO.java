package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


public class QuestionCreateDTO {
    private String intitule;
    private String explication;
    private Integer points;
    private Integer ordre;
    private String imageUrl;
    private UUID quizId;
    private List<QuestionOptionCreateDTO> options;

    public QuestionCreateDTO() {
    }

    public QuestionCreateDTO(String intitule, String explication, Integer points, Integer ordre, String imageUrl, UUID quizId, List<QuestionOptionCreateDTO> options) {
        this.intitule = intitule;
        this.explication = explication;
        this.points = points;
        this.ordre = ordre;
        this.imageUrl = imageUrl;
        this.quizId = quizId;
        this.options = options;
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

    public List<QuestionOptionCreateDTO> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionCreateDTO> options) {
        this.options = options;
    }
}
