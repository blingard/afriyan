package org.ligot.afriyan.Dto;

import org.ligot.afriyan.entities.RapportType;
import java.io.Serializable;
import java.util.Date;

public class PlanningFamilialLogDTO implements Serializable {
    private Long id;
    private Date dateLog;
    private boolean methodeSuivie;
    private RapportType rapportSexuel;
    private String effetsSecondaires;
    private String notes;

    public PlanningFamilialLogDTO() {
    }

    public PlanningFamilialLogDTO(Long id, Date dateLog, boolean methodeSuivie, RapportType rapportSexuel, String effetsSecondaires, String notes) {
        this.id = id;
        this.dateLog = dateLog;
        this.methodeSuivie = methodeSuivie;
        this.rapportSexuel = rapportSexuel;
        this.effetsSecondaires = effetsSecondaires;
        this.notes = notes;
    }

    public static PlanningFamilialLogDTOBuilder builder() {
        return new PlanningFamilialLogDTOBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateLog() {
        return dateLog;
    }

    public void setDateLog(Date dateLog) {
        this.dateLog = dateLog;
    }

    public boolean isMethodeSuivie() {
        return methodeSuivie;
    }

    public void setMethodeSuivie(boolean methodeSuivie) {
        this.methodeSuivie = methodeSuivie;
    }

    public RapportType getRapportSexuel() {
        return rapportSexuel;
    }

    public void setRapportSexuel(RapportType rapportSexuel) {
        this.rapportSexuel = rapportSexuel;
    }

    public String getEffetsSecondaires() {
        return effetsSecondaires;
    }

    public void setEffetsSecondaires(String effetsSecondaires) {
        this.effetsSecondaires = effetsSecondaires;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static class PlanningFamilialLogDTOBuilder {
        private Long id;
        private Date dateLog;
        private boolean methodeSuivie;
        private RapportType rapportSexuel = RapportType.AUCUN;
        private String effetsSecondaires;
        private String notes;

        public PlanningFamilialLogDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public PlanningFamilialLogDTOBuilder dateLog(Date dateLog) {
            this.dateLog = dateLog;
            return this;
        }
        public PlanningFamilialLogDTOBuilder methodeSuivie(boolean methodeSuivie) {
            this.methodeSuivie = methodeSuivie;
            return this;
        }
        public PlanningFamilialLogDTOBuilder rapportSexuel(RapportType rapportSexuel) {
            this.rapportSexuel = rapportSexuel;
            return this;
        }
        public PlanningFamilialLogDTOBuilder effetsSecondaires(String effetsSecondaires) {
            this.effetsSecondaires = effetsSecondaires;
            return this;
        }
        public PlanningFamilialLogDTOBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }
        public PlanningFamilialLogDTO build() {
            return new PlanningFamilialLogDTO(id, dateLog, methodeSuivie, rapportSexuel, effetsSecondaires, notes);
        }
    }
}
