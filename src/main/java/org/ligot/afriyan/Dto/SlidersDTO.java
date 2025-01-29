package org.ligot.afriyan.Dto;

public class SlidersDTO {
    private Long id;
    private String message;
    private String photo;
    private String name;
    private boolean status;

    public SlidersDTO() {
    }

    public SlidersDTO(Long id, String message, String photo, String name, boolean status) {
        this.id = id;
        this.message = message;
        this.photo = photo;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
