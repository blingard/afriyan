package org.ligot.afriyan.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class GalleryDTO {
    private UUID id;
    private String title;
    private String name;
    private String description;
    private GalleryType type;
    private String path;
    private String extension;
    private Long duree;
    private Long taille;
    private boolean active;



    public GalleryDTO() {
    }

    public GalleryDTO(UUID id, String title, String name, String description, GalleryType type, String path, String extension, Long duree, Long taille, boolean active) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.description = description;
        this.type = type;
        this.path = path;
        this.extension = extension;
        this.duree = duree;
        this.taille = taille;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GalleryType getType() {
        return type;
    }

    public void setType(GalleryType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
