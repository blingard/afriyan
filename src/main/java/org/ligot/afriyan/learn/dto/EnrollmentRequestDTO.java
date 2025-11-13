package org.ligot.afriyan.learn.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class EnrollmentRequestDTO {
    private UUID formationId;

    public EnrollmentRequestDTO() {
    }

    public EnrollmentRequestDTO(UUID formationId) {
        this.formationId = formationId;
    }

    public UUID getFormationId() {
        return formationId;
    }

    public void setFormationId(UUID formationId) {
        this.formationId = formationId;
    }
}
