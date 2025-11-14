package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "learn_user_chapter_progress",
       uniqueConstraints = @UniqueConstraint(columnNames = {"enrollment_id", "chapter_id"}))
public class UserChapterProgress implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private UserFormationEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapitres chapter;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    @Column(name = "date_first_view")
    private Date dateFirstView;

    @Column(name = "date_completion")
    private Date dateCompletion;

    @Column(name = "time_spent_seconds")
    private Integer timeSpentSeconds = 0; // Temps passé en secondes

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private Date dateModification;

    public UserChapterProgress() {
    }

    public UserChapterProgress(UUID id, UserFormationEnrollment enrollment, Chapitres chapter, Boolean isCompleted, Date dateFirstView, Date dateCompletion, Integer timeSpentSeconds, Date dateCreation, Date dateModification) {
        this.id = id;
        this.enrollment = enrollment;
        this.chapter = chapter;
        this.isCompleted = isCompleted;
        this.dateFirstView = dateFirstView;
        this.dateCompletion = dateCompletion;
        this.timeSpentSeconds = timeSpentSeconds;
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

    public Chapitres getChapter() {
        return chapter;
    }

    public void setChapter(Chapitres chapter) {
        this.chapter = chapter;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Date getDateFirstView() {
        return dateFirstView;
    }

    public void setDateFirstView(Date dateFirstView) {
        this.dateFirstView = dateFirstView;
    }

    public Date getDateCompletion() {
        return dateCompletion;
    }

    public void setDateCompletion(Date dateCompletion) {
        this.dateCompletion = dateCompletion;
    }

    public Integer getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public void setTimeSpentSeconds(Integer timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
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
