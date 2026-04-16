package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class GalleryRequestUpdate {
    @NotNull
    private UUID id;
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Long duree;



    public GalleryRequestUpdate() {
    }

    public GalleryRequestUpdate(@NotNull UUID id, String title, String description, Long duree) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duree = duree;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }
}
