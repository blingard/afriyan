package org.ligot.afriyan.Dto;

public class MediatechDTO {
    private Long id;

    private String path;

    private String text;
    private boolean active;

    private String contentType;
    private Long taille;

    public MediatechDTO() {
    }

    public MediatechDTO(Long id, String path, String text, boolean active, String contentType, Long taille) {
        this.id = id;
        this.path = path;
        this.text = text;
        this.active = active;
        this.contentType = contentType;
        this.taille = taille;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }
}
