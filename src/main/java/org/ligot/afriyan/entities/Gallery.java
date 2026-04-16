package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import org.ligot.afriyan.Dto.GalleryType;

import java.util.UUID;

@Entity
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(nullable = false)
    private Long taille;
    @Column(nullable = false)
    private String extension;
    @Column(nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GalleryType type;

    @Column(length = 255, nullable = false, unique = true)
    private String path;

    private Long duree;

    private boolean active;

    public Gallery() {
    }

    public Gallery(UUID id, String title, Long taille, String extension, String name, String description, GalleryType type, String path, Long duree, boolean active) {
        this.id = id;
        this.title = title;
        this.taille = taille;
        this.extension = extension;
        this.name = name;
        this.description = description;
        this.type = type;
        this.path = path;
        this.duree = duree;
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

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
