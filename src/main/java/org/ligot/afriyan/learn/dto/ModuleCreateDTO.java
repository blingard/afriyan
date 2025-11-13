package org.ligot.afriyan.learn.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ModuleCreateDTO {
    private String titre;
    private String description;
    private Integer ordre;
    private Integer dureeEstimee;
    private Boolean withQuiz;
    private UUID formationId;

    public ModuleCreateDTO() {
    }

    public ModuleCreateDTO(String titre, String description, Integer ordre, Integer dureeEstimee, Boolean withQuiz, UUID formationId) {
        this.titre = titre;
        this.description = description;
        this.ordre = ordre;
        this.dureeEstimee = dureeEstimee;
        this.withQuiz = withQuiz;
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

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public Integer getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(Integer dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }

    public Boolean getWithQuiz() {
        return withQuiz;
    }

    public void setWithQuiz(Boolean withQuiz) {
        this.withQuiz = withQuiz;
    }

    public UUID getFormationId() {
        return formationId;
    }

    public void setFormationId(UUID formationId) {
        this.formationId = formationId;
    }
}
