package org.ligot.afriyan.sondage.dto;

import java.util.HashSet;
import java.util.Set;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;

public class AnswerDTO {
    private Long id;
    private TypeResponse typeResponse;
    private Set<ModelResponseDTO> values = new HashSet<>(0);
    private TypeUserSondage typeUser;

    public AnswerDTO() {
    }

    public AnswerDTO(Long id) {
        this.id = id;
    }

    public AnswerDTO(Long id, TypeResponse typeResponse, Set<ModelResponseDTO> values, TypeUserSondage typeUser) {
        this.id = id;
        this.typeResponse = typeResponse;
        this.values = values;
        this.typeUser = typeUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeResponse getTypeResponse() {
        return typeResponse;
    }

    public void setTypeResponse(TypeResponse typeResponse) {
        this.typeResponse = typeResponse;
    }

    public Set<ModelResponseDTO> getValues() {
        return values;
    }

    public void setValues(Set<ModelResponseDTO> values) {
        this.values = values;
    }

    public TypeUserSondage getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUserSondage typeUser) {
        this.typeUser = typeUser;
    }
}
