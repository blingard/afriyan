package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GalleryRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private GalleryType type;

    private Long duree;



    public GalleryRequest() {
    }

    public GalleryRequest(String title, String description, GalleryType type, Long duree) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.duree = duree;
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

    public GalleryType getType() {
        return type;
    }

    public void setType(GalleryType type) {
        this.type = type;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }
}
