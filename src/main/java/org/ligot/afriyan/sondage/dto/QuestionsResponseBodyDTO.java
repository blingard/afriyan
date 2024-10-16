package org.ligot.afriyan.sondage.dto;

import org.ligot.afriyan.sondage.enumerations.TypeResponse;

import java.util.HashSet;
import java.util.Set;

public class QuestionsResponseBodyDTO {
    private Long question;
    private Long response;

    public QuestionsResponseBodyDTO() {
    }

    public QuestionsResponseBodyDTO(Long question, Long response) {
        this.question = question;
        this.response = response;
    }

    public Long getQuestion() {
        return question;
    }

    public void setQuestion(Long question) {
        this.question = question;
    }

    public Long getResponse() {
        return response;
    }

    public void setResponse(Long response) {
        this.response = response;
    }
}
