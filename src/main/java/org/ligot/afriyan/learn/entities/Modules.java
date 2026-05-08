package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learn_modules")
public class Modules implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer ordre; // Ordre du module dans la formation

    @Column(name = "duree_estimee")
    private Integer dureeEstimee; // En minutes

    @Column(name = "with_quiz")
    private Boolean withQuiz = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @OneToOne(mappedBy = "module", cascade = CascadeType.ALL)
    private Quiz quiz;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapitres> chapitres = new ArrayList<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProgress> userProgresses = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;



    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    public Modules() {
    }

    public Modules(UUID id, String titre, String description, Integer ordre, Integer dureeEstimee, Boolean withQuiz, Formation formation, Quiz quiz, List<Chapitres> chapitres, List<UserProgress> userProgresses, Date dateCreation, Date dateModification, boolean active) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.ordre = ordre;
        this.dureeEstimee = dureeEstimee;
        this.withQuiz = withQuiz;
        this.formation = formation;
        this.quiz = quiz;
        this.chapitres = chapitres;
        this.userProgresses = userProgresses;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public Integer getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(Integer dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }

    public Boolean getWithQuiz() {
        return withQuiz;
    }

    public void setWithQuiz(Boolean withQuiz) {
        this.withQuiz = withQuiz;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Chapitres> getChapitres() {
        return chapitres;
    }

    public void setChapitres(List<Chapitres> chapitres) {
        this.chapitres = chapitres;
    }

    public List<UserProgress> getUserProgresses() {
        return userProgresses;
    }

    public void setUserProgresses(List<UserProgress> userProgresses) {
        this.userProgresses = userProgresses;
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
