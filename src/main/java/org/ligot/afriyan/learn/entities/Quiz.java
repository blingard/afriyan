package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learn_quiz")
public class Quiz implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuizType type;

    @Column(name = "score_minimum")
    private Integer scoreMinimum = 50; // Score minimum pour réussir (en %)

    @Column(name = "duree_limite")
    private Integer dureeLimite; // Durée en minutes (null = pas de limite)

    @Column(name = "nombre_tentatives_max")
    private Integer nombreTentativesMax; // null = illimité

    @OneToOne
    @JoinColumn(name = "module_id")
    private Modules module;

    @OneToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserQuizAttempt> attempts;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    public enum QuizType {
        MODULE_QUIZ,    // Quiz de fin de module
        FINAL_QUIZ      // Quiz de fin de formation
    }

    public Quiz() {
    }

    public Quiz(UUID id, String titre, String description, QuizType type, Integer scoreMinimum, Integer dureeLimite, Integer nombreTentativesMax, Modules module, Formation formation, List<Question> questions, List<UserQuizAttempt> attempts, Date dateCreation, Date dateModification, boolean active) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.scoreMinimum = scoreMinimum;
        this.dureeLimite = dureeLimite;
        this.nombreTentativesMax = nombreTentativesMax;
        this.module = module;
        this.formation = formation;
        this.questions = questions;
        this.attempts = attempts;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuizType getType() {
        return type;
    }

    public void setType(QuizType type) {
        this.type = type;
    }

    public Integer getScoreMinimum() {
        return scoreMinimum;
    }

    public void setScoreMinimum(Integer scoreMinimum) {
        this.scoreMinimum = scoreMinimum;
    }

    public Integer getDureeLimite() {
        return dureeLimite;
    }

    public void setDureeLimite(Integer dureeLimite) {
        this.dureeLimite = dureeLimite;
    }

    public Integer getNombreTentativesMax() {
        return nombreTentativesMax;
    }

    public void setNombreTentativesMax(Integer nombreTentativesMax) {
        this.nombreTentativesMax = nombreTentativesMax;
    }

    public Modules getModule() {
        return module;
    }

    public void setModule(Modules module) {
        this.module = module;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<UserQuizAttempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<UserQuizAttempt> attempts) {
        this.attempts = attempts;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
