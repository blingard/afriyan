package org.ligot.afriyan.Dto;

import jakarta.persistence.*;

public class ThemeTraiterDTO {
    private Long id;
    private String title;
    private boolean active;

    public ThemeTraiterDTO() {
    }

    public ThemeTraiterDTO(Long id, String title, boolean active) {
        this.id = id;
        this.title = title;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
