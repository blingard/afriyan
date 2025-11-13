package org.ligot.afriyan.echo.entities;


import jakarta.persistence.*;
import org.ligot.afriyan.echo.AlertStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AlertRiskType {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String code;

    private String icon;
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean state;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public AlertRiskType() {
    }

    public AlertRiskType(UUID id, String code, String icon, String description, boolean state, LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.code = code;
        this.icon = icon;
        this.description = description;
        this.state = state;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
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


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}