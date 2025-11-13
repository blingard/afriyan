package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.ligot.afriyan.entities.Utilisateur;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learn_user_formation_enrollments", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "formation_id"}))
public class UserFormationEnrollment implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Utilisateur user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.IN_PROGRESS;

    @Column(name = "progression_pourcent")
    private Double progressionPourcent = 0.0; // Pourcentage de progression global

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "date_fin")
    private Date dateFin;

    @Column(name = "score_final")
    private Double scoreFinal; // Score du quiz final

    @Column(name = "certificat_url")
    private String certificatUrl; // URL du certificat si formation complétée

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProgress> progresses;

    @CreationTimestamp
    @Column(name = "date_inscription", updatable = false)
    private Date dateInscription;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;

    public enum EnrollmentStatus {
        IN_PROGRESS,    // En cours
        COMPLETED,      // Terminée avec succès
        FAILED,         // Échouée (quiz final raté)
        ABANDONED       // Abandonnée
    }

    public UserFormationEnrollment() {
    }

    public UserFormationEnrollment(UUID id, Utilisateur user, Formation formation, EnrollmentStatus status, Double progressionPourcent, Date dateDebut, Date dateFin, Double scoreFinal, String certificatUrl, List<UserProgress> progresses, Date dateInscription, Date dateModification) {
        this.id = id;
        this.user = user;
        this.formation = formation;
        this.status = status;
        this.progressionPourcent = progressionPourcent;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.scoreFinal = scoreFinal;
        this.certificatUrl = certificatUrl;
        this.progresses = progresses;
        this.dateInscription = dateInscription;
        this.dateModification = dateModification;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
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

    public List<UserProgress> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<UserProgress> progresses) {
        this.progresses = progresses;
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
}
