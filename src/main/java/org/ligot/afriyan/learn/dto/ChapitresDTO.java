package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

public class ChapitresDTO {
    private UUID id;
    private String titre;
    private String contenu;
    private Integer ordre;
    private Integer dureeEstimee;
    private String videoUrl;
    private String documentUrl;
    private UUID moduleId;
    private Date dateCreation;
    private Date dateModification;

    public ChapitresDTO() {
    }

    public ChapitresDTO(UUID id, String titre, String contenu, Integer ordre, Integer dureeEstimee, String videoUrl, String documentUrl, UUID moduleId, Date dateCreation, Date dateModification) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.ordre = ordre;
        this.dureeEstimee = dureeEstimee;
        this.videoUrl = videoUrl;
        this.documentUrl = documentUrl;
        this.moduleId = moduleId;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public UUID getModuleId() {
        return moduleId;
    }

    public void setModuleId(UUID moduleId) {
        this.moduleId = moduleId;
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
