package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

public class SubmitQuizDTO {
    private UUID attemptId;
    private List<QuizAnswerDTO> answers;

    public SubmitQuizDTO() {
    }

    public SubmitQuizDTO(UUID attemptId, List<QuizAnswerDTO> answers) {
        this.attemptId = attemptId;
        this.answers = answers;
    }

    public UUID getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(UUID attemptId) {
        this.attemptId = attemptId;
    }

    public List<QuizAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuizAnswerDTO> answers) {
        this.answers = answers;
    }
}
