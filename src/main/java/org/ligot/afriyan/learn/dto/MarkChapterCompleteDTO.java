package org.ligot.afriyan.learn.dto;

import java.util.UUID;

public class MarkChapterCompleteDTO {

    private UUID enrollmentId;
    private UUID chapterId;
    private Integer timeSpentSeconds; // Temps passé sur le chapitre en secondes (optionnel)

    public MarkChapterCompleteDTO() {
    }

    public MarkChapterCompleteDTO(UUID enrollmentId, UUID chapterId, Integer timeSpentSeconds) {
        this.enrollmentId = enrollmentId;
        this.chapterId = chapterId;
        this.timeSpentSeconds = timeSpentSeconds;
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public UUID getChapterId() {
        return chapterId;
    }

    public void setChapterId(UUID chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public void setTimeSpentSeconds(Integer timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
    }
}
