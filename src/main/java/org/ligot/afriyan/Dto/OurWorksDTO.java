package org.ligot.afriyan.Dto;

import org.ligot.afriyan.entities.OurWorksType;

public class OurWorksDTO {
    private Long id;
    private String title;
    private String text;
    private String photo;
    private OurWorksType ourWorksType;
    private boolean status;

    public OurWorksDTO() {
    }

    public OurWorksDTO(Long id) {
        this.id = id;
    }

    public OurWorksDTO(Long id, String title, String text, String photo, OurWorksType ourWorksType, boolean status) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.photo = photo;
        this.ourWorksType = ourWorksType;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public OurWorksType getOurWorksType() {
        return ourWorksType;
    }

    public void setOurWorksType(OurWorksType ourWorksType) {
        this.ourWorksType = ourWorksType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
