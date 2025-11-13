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
@Table(name = "learn_questions")
public class Question implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String intitule;

    @Column(columnDefinition = "TEXT")
    private String explication; // Explication de la réponse correcte

    @Column(nullable = false)
    private Integer points = 1; // Points attribués pour cette question

    @Column(nullable = false)
    private Integer ordre; // Ordre de la question dans le quiz

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl; // Image optionnelle pour la question

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> options;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserQuizAnswer> userAnswers;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;

    public Question() {
    }

    public Question(UUID id, String intitule, String explication, Integer points, Integer ordre, String imageUrl, Quiz quiz, List<QuestionOption> options, List<UserQuizAnswer> userAnswers, Date dateCreation, Date dateModification) {
        this.id = id;
        this.intitule = intitule;
        this.explication = explication;
        this.points = points;
        this.ordre = ordre;
        this.imageUrl = imageUrl;
        this.quiz = quiz;
        this.options = options;
        this.userAnswers = userAnswers;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getExplication() {
        return explication;
    }

    public void setExplication(String explication) {
        this.explication = explication;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<QuestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOption> options) {
        this.options = options;
    }

    public List<UserQuizAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserQuizAnswer> userAnswers) {
        this.userAnswers = userAnswers;
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
