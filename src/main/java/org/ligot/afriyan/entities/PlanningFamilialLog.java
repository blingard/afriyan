package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "planning_familial_logs")
public class PlanningFamilialLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planning_familial_id", nullable = false)
    private PlanningFamilial planningFamilial;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_log", nullable = false)
    private Date dateLog;

    @Column(name = "methode_suivie", nullable = false)
    private boolean methodeSuivie = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "rapport_sexuel", nullable = false)
    private RapportType rapportSexuel = RapportType.AUCUN;

    @Column(name = "effets_secondaires")
    private String effetsSecondaires;

    @Column(name = "notes")
    private String notes;

    public PlanningFamilialLog() {
    }

    public PlanningFamilialLog(Long id, PlanningFamilial planningFamilial, Date dateLog, boolean methodeSuivie, RapportType rapportSexuel, String effetsSecondaires, String notes) {
        this.id = id;
        this.planningFamilial = planningFamilial;
        this.dateLog = dateLog;
        this.methodeSuivie = methodeSuivie;
        this.rapportSexuel = rapportSexuel;
        this.effetsSecondaires = effetsSecondaires;
        this.notes = notes;
    }

    public static PlanningFamilialLogBuilder builder() {
        return new PlanningFamilialLogBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanningFamilial getPlanningFamilial() {
        return planningFamilial;
    }

    public void setPlanningFamilial(PlanningFamilial planningFamilial) {
        this.planningFamilial = planningFamilial;
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

    public static class PlanningFamilialLogBuilder {
        private Long id;
        private PlanningFamilial planningFamilial;
        private Date dateLog;
        private boolean methodeSuivie = false;
        private RapportType rapportSexuel = RapportType.AUCUN;
        private String effetsSecondaires;
        private String notes;

        public PlanningFamilialLogBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public PlanningFamilialLogBuilder planningFamilial(PlanningFamilial planningFamilial) {
            this.planningFamilial = planningFamilial;
            return this;
        }
        public PlanningFamilialLogBuilder dateLog(Date dateLog) {
            this.dateLog = dateLog;
            return this;
        }
        public PlanningFamilialLogBuilder methodeSuivie(boolean methodeSuivie) {
            this.methodeSuivie = methodeSuivie;
            return this;
        }
        public PlanningFamilialLogBuilder rapportSexuel(RapportType rapportSexuel) {
            this.rapportSexuel = rapportSexuel;
            return this;
        }
        public PlanningFamilialLogBuilder effetsSecondaires(String effetsSecondaires) {
            this.effetsSecondaires = effetsSecondaires;
            return this;
        }
        public PlanningFamilialLogBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }
        public PlanningFamilialLog build() {
            return new PlanningFamilialLog(id, planningFamilial, dateLog, methodeSuivie, rapportSexuel, effetsSecondaires, notes);
        }
    }
}
