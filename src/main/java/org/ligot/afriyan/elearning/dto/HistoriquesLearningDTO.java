package org.ligot.afriyan.elearning.dto;

import org.ligot.afriyan.elearning.entities.HistoriquesLearning;

import java.time.Instant;

public class HistoriquesLearningDTO implements Comparable<HistoriquesLearningDTO>{
    private Long id;

    private Long userId;
    private Long formationId;
    private Long chapitreId;

    private boolean status;
    private boolean quizzPass;

    private Instant dateLecture;
    private Long nextChapter;

    public HistoriquesLearningDTO() {
    }

    public HistoriquesLearningDTO(Long id, Long userId, Long formationId, Long chapitreId, boolean status, boolean quizzPass, Instant dateLecture, Long nextChapter) {
        this.id = id;
        this.userId = userId;
        this.formationId = formationId;
        this.chapitreId = chapitreId;
        this.status = status;
        this.quizzPass = quizzPass;
        this.dateLecture = dateLecture;
        this.nextChapter = nextChapter;
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

    @Override
    public int compareTo(HistoriquesLearningDTO o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return "HistoriquesLearningDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", formationId=" + formationId +
                ", chapitreId='" + chapitreId + '\'' +
                ", status=" + status +
                ", quizzPass=" + quizzPass +
                ", dateLecture=" + dateLecture +
                ", nextChapter=" + nextChapter +
                '}';
    }
}
