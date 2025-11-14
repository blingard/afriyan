package org.ligot.afriyan.partenaire.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "partenaires")
public class Partenaire implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String imageBase64;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean statut = true;

    @Column(nullable = false)
    private Boolean publish = false;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;

    public Partenaire() {
    }

    public Partenaire(UUID id, String nom, String imageBase64, String description, Boolean statut, Boolean publish, Date dateCreation, Date dateModification) {
        this.id = id;
        this.nom = nom;
        this.imageBase64 = imageBase64;
        this.description = description;
        this.statut = statut;
        this.publish = publish;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
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
