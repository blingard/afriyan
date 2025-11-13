package org.ligot.afriyan.learn.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UpdateProgressDTO {
    private UUID moduleId;
    private UUID enrollmentId;
    private Double progressionPourcent;

    public UpdateProgressDTO() {
    }

    public UpdateProgressDTO(UUID moduleId, UUID enrollmentId, Double progressionPourcent) {
        this.moduleId = moduleId;
        this.enrollmentId = enrollmentId;
        this.progressionPourcent = progressionPourcent;
    }

    public UUID getModuleId() {
        return moduleId;
    }

    public void setModuleId(UUID moduleId) {
        this.moduleId = moduleId;
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Double getProgressionPourcent() {
        return progressionPourcent;
    }

    public void setProgressionPourcent(Double progressionPourcent) {
        this.progressionPourcent = progressionPourcent;
    }
}
