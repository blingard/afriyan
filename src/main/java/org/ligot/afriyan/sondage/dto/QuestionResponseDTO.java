package org.ligot.afriyan.sondage.dto;

import org.ligot.afriyan.sondage.enumerations.TypeResponse;

import java.util.HashSet;
import java.util.Set;

public class QuestionResponseDTO {
    private Long idSondage;
    private Long idFormation;
    private Set<QuestionsResponseBodyDTO> body = new HashSet<>(0);

    public QuestionResponseDTO() {
    }

    public QuestionResponseDTO(Long idSondage, Long idFormation, Set<QuestionsResponseBodyDTO> body) {
        this.idSondage = idSondage;
        this.idFormation = idFormation;
        this.body = body;
    }

    public Long getIdSondage() {
        return idSondage;
    }

    public void setIdSondage(Long idSondage) {
        this.idSondage = idSondage;
    }

    public Long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Long idFormation) {
        this.idFormation = idFormation;
    }

    public Set<QuestionsResponseBodyDTO> getBody() {
        return body;
    }

    public void setBody(Set<QuestionsResponseBodyDTO> body) {
        this.body = body;
    }
}
