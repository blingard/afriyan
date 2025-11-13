package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ChapitresCreateDTO {
    @NotNull(message = "L'ID du module est obligatoire")
    private String moduleId;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String contenu;

    @NotNull(message = "L'ordre est obligatoire")
    private Integer ordre;

    private Integer dureeEstimee;
    private String videoUrl;
    private String documentUrl;

    public ChapitresCreateDTO() {
    }

    public ChapitresCreateDTO(@NotNull(message = "L'ID du module est obligatoire") String moduleId, String titre, String contenu, @NotNull(message = "L'ordre est obligatoire") Integer ordre, Integer dureeEstimee, String videoUrl, String documentUrl) {
        this.moduleId = moduleId;
        this.titre = titre;
        this.contenu = contenu;
        this.ordre = ordre;
        this.dureeEstimee = dureeEstimee;
        this.videoUrl = videoUrl;
        this.documentUrl = documentUrl;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
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
}
