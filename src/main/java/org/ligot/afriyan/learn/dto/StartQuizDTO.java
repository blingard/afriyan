package org.ligot.afriyan.learn.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class StartQuizDTO {
    private UUID quizId;
    private UUID enrollmentId;

    public StartQuizDTO() {
    }

    public StartQuizDTO(UUID quizId, UUID enrollmentId) {
        this.quizId = quizId;
        this.enrollmentId = enrollmentId;
    }

    public UUID getQuizId() {
        return quizId;
    }

    public void setQuizId(UUID quizId) {
        this.quizId = quizId;
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }
}
