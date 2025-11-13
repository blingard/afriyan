package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserFormationEnrollmentDTO {
    private UUID id;
    private Long userId;
    private String userNom;
    private String userPrenom;
    private UUID formationId;
    private String formationTitre;
    private String status;
    private Double progressionPourcent;
    private Date dateDebut;
    private Date dateFin;
    private Double scoreFinal;
    private String certificatUrl;
    private Date dateInscription;
    private Date dateModification;
    private List<UserProgressDTO> progresses;

    public UserFormationEnrollmentDTO() {
    }

    public UserFormationEnrollmentDTO(UUID id, Long userId, String userNom, String userPrenom, UUID formationId, String formationTitre, String status, Double progressionPourcent, Date dateDebut, Date dateFin, Double scoreFinal, String certificatUrl, Date dateInscription, Date dateModification, List<UserProgressDTO> progresses) {
        this.id = id;
        this.userId = userId;
        this.userNom = userNom;
        this.userPrenom = userPrenom;
        this.formationId = formationId;
        this.formationTitre = formationTitre;
        this.status = status;
        this.progressionPourcent = progressionPourcent;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.scoreFinal = scoreFinal;
        this.certificatUrl = certificatUrl;
        this.dateInscription = dateInscription;
        this.dateModification = dateModification;
        this.progresses = progresses;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNom() {
        return userNom;
    }

    public void setUserNom(String userNom) {
        this.userNom = userNom;
    }

    public String getUserPrenom() {
        return userPrenom;
    }

    public void setUserPrenom(String userPrenom) {
        this.userPrenom = userPrenom;
    }

    public UUID getFormationId() {
        return formationId;
    }

    public void setFormationId(UUID formationId) {
        this.formationId = formationId;
    }

    public String getFormationTitre() {
        return formationTitre;
    }

    public void setFormationTitre(String formationTitre) {
        this.formationTitre = formationTitre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getProgressionPourcent() {
        return progressionPourcent;
    }

    public void setProgressionPourcent(Double progressionPourcent) {
        this.progressionPourcent = progressionPourcent;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Double getScoreFinal() {
        return scoreFinal;
    }

    public void setScoreFinal(Double scoreFinal) {
        this.scoreFinal = scoreFinal;
    }

    public String getCertificatUrl() {
        return certificatUrl;
    }

    public void setCertificatUrl(String certificatUrl) {
        this.certificatUrl = certificatUrl;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public List<UserProgressDTO> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<UserProgressDTO> progresses) {
        this.progresses = progresses;
    }
}
