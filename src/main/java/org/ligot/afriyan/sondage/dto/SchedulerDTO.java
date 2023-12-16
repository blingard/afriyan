package org.ligot.afriyan.sondage.dto;


import java.time.LocalDateTime;
public class SchedulerDTO {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public SchedulerDTO() {
    }

    public SchedulerDTO(Long id) {
        this.id = id;
    }

    public SchedulerDTO(Long id, LocalDateTime startDate, LocalDateTime endDate) {
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
