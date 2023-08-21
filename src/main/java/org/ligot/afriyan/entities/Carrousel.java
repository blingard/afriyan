package org.ligot.afriyan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Carrousel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String photo;
    private String Titre;
    private String description;

    private boolean status;

    public Carrousel() {
    }

    public Carrousel(Long id) {
        this.id = id;
    }

    public Carrousel(Long id, String photo, String titre, String description, boolean status) {
        this.id = id;
        this.photo = photo;
        Titre = titre;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
