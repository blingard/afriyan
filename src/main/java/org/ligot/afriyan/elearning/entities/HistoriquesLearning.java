package org.ligot.afriyan.elearning.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "historique")
public class HistoriquesLearning implements Comparable<HistoriquesLearning>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long formationId;
    private Long moduleId;
    private Long chapitreId;

    private boolean status;
    private boolean quizzPass;

    private Instant dateLecture;
    private Long nextChapter;
    private Long nextModule;
    private Long previousChapter;
    private Long previousModule;

    public HistoriquesLearning() {
    }

    public HistoriquesLearning(Long id, Long userId, Long formationId, Long moduleId, Long chapitreId, boolean status, boolean quizzPass, Instant dateLecture, Long nextChapter, Long nextModule, Long previousChapter, Long previousModule) {
        this.id = id;
        this.userId = userId;
        this.formationId = formationId;
        this.moduleId = moduleId;
        this.chapitreId = chapitreId;
        this.status = status;
        this.quizzPass = quizzPass;
        this.dateLecture = dateLecture;
        this.nextChapter = nextChapter;
        this.nextModule = nextModule;
        this.previousChapter = previousChapter;
        this.previousModule = previousModule;
    }

    public Long getPreviousChapter() {
        return previousChapter;
    }

    public void setPreviousChapter(Long previousChapter) {
        this.previousChapter = previousChapter;
    }

    public Long getPreviousModule() {
        return previousModule;
    }

    public void setPreviousModule(Long previousModule) {
        this.previousModule = previousModule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFormationId() {
        return formationId;
    }

    public void setFormationId(Long formationId) {
        this.formationId = formationId;
    }

    public Long getChapitreId() {
        return chapitreId;
    }

    public void setChapitreId(Long chapitreId) {
        this.chapitreId = chapitreId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isQuizzPass() {
        return quizzPass;
    }

    public void setQuizzPass(boolean quizzPass) {
        this.quizzPass = quizzPass;
    }

    public Instant getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(Instant dateLecture) {
        this.dateLecture = dateLecture;
    }

    public Long getNextChapter() {
        return nextChapter;
    }

    public void setNextChapter(Long nextChapter) {
        this.nextChapter = nextChapter;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getNextModule() {
        return nextModule;
    }

    public void setNextModule(Long nextModule) {
        this.nextModule = nextModule;
    }

    @Override
    public int compareTo(HistoriquesLearning o) {
        return this.id.compareTo(o.id);
    }
}
