package org.ligot.afriyan.learn.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class QuizCreateDTO {
    private String titre;
    private String description;
    private String type; // MODULE_QUIZ ou FINAL_QUIZ
    private Integer scoreMinimum;
    private Integer dureeLimite;
    private Integer nombreTentativesMax;
    private UUID moduleId;
    private UUID formationId;

    public QuizCreateDTO() {
    }

    public QuizCreateDTO(String titre, String description, String type, Integer scoreMinimum, Integer dureeLimite, Integer nombreTentativesMax, UUID moduleId, UUID formationId) {
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.scoreMinimum = scoreMinimum;
        this.dureeLimite = dureeLimite;
        this.nombreTentativesMax = nombreTentativesMax;
        this.moduleId = moduleId;
        this.formationId = formationId;
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
}
