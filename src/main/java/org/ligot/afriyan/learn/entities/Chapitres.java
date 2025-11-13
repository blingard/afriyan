package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(name = "LearnChapitres")
@Table(name = "learn_chapitres")
public class Chapitres implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu; // Contenu du chapitre (texte, HTML, etc.)

    @Column(nullable = false)
    private Integer ordre; // Ordre du chapitre dans le module

    @Column(name = "duree_estimee")
    private Integer dureeEstimee; // En minutes

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "document_url")
    private String documentUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Modules module;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;

    public UUID getId() {
        return id;
    }

    public Chapitres(UUID id, String titre, String contenu, Integer ordre, Integer dureeEstimee, String videoUrl, String documentUrl, Modules module, Date dateCreation, Date dateModification) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.ordre = ordre;
        this.dureeEstimee = dureeEstimee;
        this.videoUrl = videoUrl;
        this.documentUrl = documentUrl;
        this.module = module;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
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

    public Modules getModule() {
        return module;
    }

    public void setModule(Modules module) {
        this.module = module;
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

    public Chapitres() {
    }
}
