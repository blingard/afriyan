package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
public class Categories {
    @Id
    @GeneratedValue
    private UUID id;
    @Column (name = "code", unique = true, nullable = false)
    private String code;
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean status;


    public Categories() {
    }

    public Categories(UUID id, String code, String description, boolean status) {
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
