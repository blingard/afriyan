package org.ligot.afriyan.learn.dto;

import java.util.List;
import java.util.UUID;


public class QuizUSerDTO {
    private UUID id;
    private String titre;
    private String description;
    private String type;
    private Integer dureeLimite;
    private Integer nombreTentativesMax;
    private List<QuestionUserDTO> questions;

    public QuizUSerDTO() {
    }

    public QuizUSerDTO(UUID id, String titre, String description, String type, Integer dureeLimite, Integer nombreTentativesMax, List<QuestionUserDTO> questions) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.dureeLimite = dureeLimite;
        this.nombreTentativesMax = nombreTentativesMax;
        this.questions = questions;
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

    public List<QuestionUserDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionUserDTO> questions) {
        this.questions = questions;
    }

}
