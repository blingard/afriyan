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
import java.util.UUID;

@Entity
@Table(name = "learn_user_progress",
       uniqueConstraints = @UniqueConstraint(columnNames = {"enrollment_id", "module_id"}))
public class UserProgress implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private UserFormationEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Modules module;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressStatus status = ProgressStatus.NOT_STARTED;

    @Column(name = "progression_pourcent")
    private Double progressionPourcent = 0.0;

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "date_completion")
    private Date dateCompletion;

    @Column(name = "score_quiz")
    private Double scoreQuiz; // Score du quiz du module (si applicable)

    @Column(name = "quiz_passed")
    private Boolean quizPassed; // true si quiz passé avec succès

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;

    public enum ProgressStatus {
        NOT_STARTED,    // Non commencé
        IN_PROGRESS,    // En cours
        COMPLETED       // Terminé
    }

    public UserProgress() {
    }

    public UserProgress(UUID id, UserFormationEnrollment enrollment, Modules module, ProgressStatus status, Double progressionPourcent, Date dateDebut, Date dateCompletion, Double scoreQuiz, Boolean quizPassed, Date dateCreation, Date dateModification) {
        this.id = id;
        this.enrollment = enrollment;
        this.module = module;
        this.status = status;
        this.progressionPourcent = progressionPourcent;
        this.dateDebut = dateDebut;
        this.dateCompletion = dateCompletion;
        this.scoreQuiz = scoreQuiz;
        this.quizPassed = quizPassed;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserFormationEnrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(UserFormationEnrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Modules getModule() {
        return module;
    }

    public void setModule(Modules module) {
        this.module = module;
    }

    public ProgressStatus getStatus() {
        return status;
    }

    public void setStatus(ProgressStatus status) {
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

    public Date getDateCompletion() {
        return dateCompletion;
    }

    public void setDateCompletion(Date dateCompletion) {
        this.dateCompletion = dateCompletion;
    }

    public Double getScoreQuiz() {
        return scoreQuiz;
    }

    public void setScoreQuiz(Double scoreQuiz) {
        this.scoreQuiz = scoreQuiz;
    }

    public Boolean getQuizPassed() {
        return quizPassed;
    }

    public void setQuizPassed(Boolean quizPassed) {
        this.quizPassed = quizPassed;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
}
