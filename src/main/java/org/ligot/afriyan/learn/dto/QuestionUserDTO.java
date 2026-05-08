package org.ligot.afriyan.learn.dto;

import java.util.List;
import java.util.UUID;

public class QuestionUserDTO {
    private UUID id;
    private String intitule;
    private String explication;
    private Integer points;
    private Integer ordre;
    private String imageUrl;
    private List<QuestionOptionUserDTO> options;

    public QuestionUserDTO() {
    }

    public QuestionUserDTO(UUID id, String intitule, String explication, Integer points, Integer ordre, String imageUrl, List<QuestionOptionUserDTO> options) {
        this.id = id;
        this.intitule = intitule;
        this.explication = explication;
        this.points = points;
        this.ordre = ordre;
        this.imageUrl = imageUrl;
        this.options = options;
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

    public List<QuestionOptionUserDTO> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionUserDTO> options) {
        this.options = options;
    }
}
