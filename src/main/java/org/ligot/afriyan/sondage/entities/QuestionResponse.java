package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question_response")
public class QuestionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionId;
    private Long sondageId;
    private Long responseId;

    public QuestionResponse() {
    }

    public QuestionResponse(Long id, Long questionId, Long sondageId, Long responseId) {
        this.id = id;
        this.questionId = questionId;
        this.sondageId = sondageId;
        this.responseId = responseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getSondageId() {
        return sondageId;
    }

    public void setSondageId(Long sondageId) {
        this.sondageId = sondageId;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }
}
