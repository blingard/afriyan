package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduler")
public class Scheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Scheduler() {
    }

    public Scheduler(Long id) {
        this.id = id;
    }

    public Scheduler(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
