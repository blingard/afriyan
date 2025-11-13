package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.ligot.afriyan.entities.Categories;
import org.ligot.afriyan.learn.enumerations.FormationLevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "formations_learn")
public class Formation implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "image_couverture", columnDefinition = "TEXT", nullable = false)
    private String imageCouverture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FormationLevel niveau; // Débutant, Intermédiaire, Avancé

    @Column(name = "duree_estimee", nullable = false)
    private Integer dureeEstimee; // En heures

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormationStatus status = FormationStatus.DRAFT;

    @Column(name = "with_final_quiz")
    private Boolean withFinalQuiz = false;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Modules> modules = new ArrayList<>();

    @OneToOne(mappedBy = "formation", cascade = CascadeType.ALL)
    private Quiz quizFinal;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFormationEnrollment> enrollments;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories categories;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;

    @Column(name = "created_by")
    private Long createdBy;

    public enum FormationStatus {
        DRAFT, PUBLISHED, ARCHIVED
    }

    public Formation() {
    }

    public Formation(UUID id, String titre, String description, String imageCouverture, FormationLevel niveau, Integer dureeEstimee, FormationStatus status, Boolean withFinalQuiz, List<Modules> modules, Quiz quizFinal, List<UserFormationEnrollment> enrollments, Date dateCreation, Date dateModification, Long createdBy, Categories categories) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.imageCouverture = imageCouverture;
        this.niveau = niveau;
        this.dureeEstimee = dureeEstimee;
        this.status = status;
        this.withFinalQuiz = withFinalQuiz;
        this.modules = modules;
        this.quizFinal = quizFinal;
        this.enrollments = enrollments;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.createdBy = createdBy;
        this.categories = categories;
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

    public String getImageCouverture() {
        return imageCouverture;
    }

    public void setImageCouverture(String imageCouverture) {
        this.imageCouverture = imageCouverture;
    }

    public FormationLevel getNiveau() {
        return niveau;
    }

    public void setNiveau(FormationLevel niveau) {
        this.niveau = niveau;
    }

    public Integer getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(Integer dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }

    public FormationStatus getStatus() {
        return status;
    }

    public void setStatus(FormationStatus status) {
        this.status = status;
    }

    public Boolean getWithFinalQuiz() {
        return withFinalQuiz;
    }

    public void setWithFinalQuiz(Boolean withFinalQuiz) {
        this.withFinalQuiz = withFinalQuiz;
    }

    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(List<Modules> modules) {
        this.modules = modules;
    }

    public Quiz getQuizFinal() {
        return quizFinal;
    }

    public void setQuizFinal(Quiz quizFinal) {
        this.quizFinal = quizFinal;
    }

    public List<UserFormationEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<UserFormationEnrollment> enrollments) {
        this.enrollments = enrollments;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public Categories getCategories() {
        return categories;
    }
    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
