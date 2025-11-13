package org.ligot.afriyan.echo.entities;


import jakarta.persistence.*;
import org.ligot.afriyan.echo.AlertStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Alerts {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;

    @ManyToOne
    private AlertRiskType riskType; // Nature du risque: SECHERESSE, INONDATION, CONFLIT, VBG [5, 9]

    @ManyToOne
    private Localities localities;

    @Enumerated(EnumType.STRING)
    private AlertStatus status; // Statut de l'alerte: PENDING_CCPR_REVIEW, ESCALATED_TO_AUTHORITY, ALERT_ACTIVE, REJECTED, DISMISSED

    private String reporterUserId; // ID de l'utilisateur qui a signalé l'alerte (ex: membre CC) [60]
    private String currentValidatorRole; // Rôle attendu pour la prochaine étape de validation (ex: CCPR, AUTORITE_LOCALE)
    private String validationComments; // Commentaires liés à la validation

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public Alerts() {
    }

    public Alerts(UUID id, String title, String description, AlertRiskType riskType, Localities localities, AlertStatus status, String reporterUserId, String currentValidatorRole, String validationComments, LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.riskType = riskType;
        this.localities = localities;
        this.status = status;
        this.reporterUserId = reporterUserId;
        this.currentValidatorRole = currentValidatorRole;
        this.validationComments = validationComments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AlertRiskType getRiskType() {
        return riskType;
    }

    public void setRiskType(AlertRiskType riskType) {
        this.riskType = riskType;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public String getReporterUserId() {
        return reporterUserId;
    }

    public void setReporterUserId(String reporterUserId) {
        this.reporterUserId = reporterUserId;
    }

    public String getCurrentValidatorRole() {
        return currentValidatorRole;
    }

    public void setCurrentValidatorRole(String currentValidatorRole) {
        this.currentValidatorRole = currentValidatorRole;
    }

    public String getValidationComments() {
        return validationComments;
    }

    public void setValidationComments(String validationComments) {
        this.validationComments = validationComments;
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

    public Localities getLocalities() {
        return localities;
    }

    public void setLocalities(Localities localities) {
        this.localities = localities;
    }
}