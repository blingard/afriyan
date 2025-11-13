package org.ligot.afriyan.entities;

import jakarta.persistence.*;

@Entity
public class Sliders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private String photo;
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FrontType frontType;

    private boolean status;

    public Sliders() {
    }

    public Sliders(Long id, String message, String photo, String title, FrontType frontType, boolean status) {
        this.id = id;
        this.message = message;
        this.photo = photo;
        this.title = title;
        this.frontType = frontType;
        this.status = status;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public FrontType getFrontType() {
        return frontType;
    }

    public void setFrontType(FrontType frontType) {
        this.frontType = frontType;
    }
}
