package org.ligot.afriyan.echo.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AlertsTemplateMessage {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String message;

    @ManyToOne
    private AlertRiskType riskType; // Nature du risque: SECHERESSE, INONDATION, CONFLIT, VBG [5, 9]

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public AlertsTemplateMessage() {
    }

    public AlertsTemplateMessage(UUID id, String title, String message, AlertRiskType riskType, LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.riskType = riskType;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AlertRiskType getRiskType() {
        return riskType;
    }

    public void setRiskType(AlertRiskType riskType) {
        this.riskType = riskType;
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
}