package org.ligot.afriyan.Dto;

import java.io.Serializable;
import java.util.Date;

public class PeriodLogDTO implements Serializable {
    private Long id;
    private Date dateDebut;
    private Date dateFin;

    public PeriodLogDTO() {
    }

    public PeriodLogDTO(Long id, Date dateDebut, Date dateFin) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public static PeriodLogDTOBuilder builder() {
        return new PeriodLogDTOBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public static class PeriodLogDTOBuilder {
        private Long id;
        private Date dateDebut;
        private Date dateFin;

        public PeriodLogDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public PeriodLogDTOBuilder dateDebut(Date dateDebut) {
            this.dateDebut = dateDebut;
            return this;
        }
        public PeriodLogDTOBuilder dateFin(Date dateFin) {
            this.dateFin = dateFin;
            return this;
        }
        public PeriodLogDTO build() {
            return new PeriodLogDTO(id, dateDebut, dateFin);
        }
    }
}
