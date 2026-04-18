package org.ligot.afriyan.Dto;

import lombok.Builder;
import org.ligot.afriyan.entities.FrontType;

@Builder
public class SlidersSmartDTO {
    private Long id;
    private String message;
    private String photo;
    private String title;

    private FrontType frontType;
    private boolean hasContent;

    public SlidersSmartDTO() {
    }

    public SlidersSmartDTO(Long id, String message, String photo, String title, FrontType frontType, boolean hasContent) {
        this.id = id;
        this.message = message;
        this.photo = photo;
        this.title = title;
        this.frontType = frontType;
        this.hasContent = hasContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FrontType getFrontType() {
        return frontType;
    }

    public void setFrontType(FrontType frontType) {
        this.frontType = frontType;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }
}
