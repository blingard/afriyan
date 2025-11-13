package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.ligot.afriyan.entities.Utilisateur;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learn_user_quiz_attempts")
public class UserQuizAttempt implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Utilisateur user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id")
    private UserFormationEnrollment enrollment;

    @Column(name = "numero_tentative", nullable = false)
    private Integer numeroTentative; // Numéro de la tentative (1, 2, 3...)

    @Column(name = "score_obtenu")
    private Double scoreObtenu; // Score en pourcentage

    @Column(name = "points_obtenus")
    private Integer pointsObtenus;

    @Column(name = "points_totaux")
    private Integer pointsTotaux;

    @Column(name = "is_passed")
    private Boolean isPassed = false; // true si score >= score minimum

    @Column(name = "is_completed")
    private Boolean isCompleted = false; // true si toutes les questions ont été répondues

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserQuizAnswer> answers;

    @CreationTimestamp
    @Column(name = "date_debut", updatable = false)
    private Date dateDebut;

    @Column(name = "date_fin")
    private Date dateFin;

    @Column(name = "duree_secondes")
    private Integer dureeSecondes; // Durée de la tentative en secondes

    public UserQuizAttempt() {
    }

    public UserQuizAttempt(UUID id, Utilisateur user, Quiz quiz, UserFormationEnrollment enrollment, Integer numeroTentative, Double scoreObtenu, Integer pointsObtenus, Integer pointsTotaux, Boolean isPassed, Boolean isCompleted, List<UserQuizAnswer> answers, Date dateDebut, Date dateFin, Integer dureeSecondes) {
        this.id = id;
        this.user = user;
        this.quiz = quiz;
        this.enrollment = enrollment;
        this.numeroTentative = numeroTentative;
        this.scoreObtenu = scoreObtenu;
        this.pointsObtenus = pointsObtenus;
        this.pointsTotaux = pointsTotaux;
        this.isPassed = isPassed;
        this.isCompleted = isCompleted;
        this.answers = answers;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dureeSecondes = dureeSecondes;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public UserFormationEnrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(UserFormationEnrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Integer getNumeroTentative() {
        return numeroTentative;
    }

    public void setNumeroTentative(Integer numeroTentative) {
        this.numeroTentative = numeroTentative;
    }

    public Double getScoreObtenu() {
        return scoreObtenu;
    }

    public void setScoreObtenu(Double scoreObtenu) {
        this.scoreObtenu = scoreObtenu;
    }

    public Integer getPointsObtenus() {
        return pointsObtenus;
    }

    public void setPointsObtenus(Integer pointsObtenus) {
        this.pointsObtenus = pointsObtenus;
    }

    public Integer getPointsTotaux() {
        return pointsTotaux;
    }

    public void setPointsTotaux(Integer pointsTotaux) {
        this.pointsTotaux = pointsTotaux;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public List<UserQuizAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserQuizAnswer> answers) {
        this.answers = answers;
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

    public Integer getDureeSecondes() {
        return dureeSecondes;
    }

    public void setDureeSecondes(Integer dureeSecondes) {
        this.dureeSecondes = dureeSecondes;
    }
}
