package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ligot.afriyan.learn.entities.Chapitres;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ModuleDTO {
    private UUID id;
    private String titre;
    private String description;
    private Integer ordre;
    private Integer dureeEstimee;
    private Boolean withQuiz;
    private UUID formationId;
    private Date dateCreation;
    private Date dateModification;
    private List<ChapitresDTO> chapitres = new ArrayList<>();
    private QuizDTO quiz;

    public ModuleDTO() {
    }

    public ModuleDTO(UUID id, String titre, String description, Integer ordre, Integer dureeEstimee, Boolean withQuiz, UUID formationId, Date dateCreation, Date dateModification, List<ChapitresDTO> chapitres, QuizDTO quiz) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.ordre = ordre;
        this.dureeEstimee = dureeEstimee;
        this.withQuiz = withQuiz;
        this.formationId = formationId;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.chapitres = chapitres;
        this.quiz = quiz;
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

    public QuizDTO getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizDTO quiz) {
        this.quiz = quiz;
    }

    public List<ChapitresDTO> getChapitres() {
        return chapitres;
    }
    public void setChapitres(List<ChapitresDTO> chapitres) {
        this.chapitres = chapitres;
    }
}
