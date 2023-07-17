package org.ligot.afriyan.Dto;

public class ValeursDTO {
    private Long id;
    private String title;
    private String description;
    private String phote;
    private boolean status;

    public ValeursDTO(Long id) {
        this.id = id;
    }

    public ValeursDTO() {
    }

    public ValeursDTO(Long id, String title, String description, String phote, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.phote = phote;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhote() {
        return phote;
    }

    public void setPhote(String phote) {
        this.phote = phote;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
