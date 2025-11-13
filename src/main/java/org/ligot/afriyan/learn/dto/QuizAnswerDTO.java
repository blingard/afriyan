package org.ligot.afriyan.learn.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class QuizAnswerDTO {
    private UUID questionId;
    private UUID selectedOptionId;

    public QuizAnswerDTO() {
    }

    public QuizAnswerDTO(UUID questionId, UUID selectedOptionId) {
        this.questionId = questionId;
        this.selectedOptionId = selectedOptionId;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public UUID getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(UUID selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }
}
