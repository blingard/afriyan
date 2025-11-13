package org.ligot.afriyan.Dto;

import java.util.UUID;

public class CategoriesDTO {
    private UUID id;
    private String code;
    private String description;

    private boolean status;


    public CategoriesDTO() {
    }

    public CategoriesDTO(UUID id, String code, String description, boolean status) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
