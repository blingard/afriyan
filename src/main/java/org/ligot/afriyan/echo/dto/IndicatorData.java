package org.ligot.afriyan.echo.dto;

import java.time.LocalDateTime;

public class IndicatorData {
    private AlertRiskTypeDTO riskType; // e.g., "INONDATION", "SECHERESSE", "CONFLIT"
    private Double rainfall; // mm
    private Double riverLevel; // m
    private Integer daysWithoutRain;
    private Integer numberOfConflicts;
    private Integer numberOfDisplacedPeople;
    private LocalDateTime observedAt;
    private LocalitiesDTO localities;

    public IndicatorData() {
    }

    public IndicatorData(AlertRiskTypeDTO riskType, Double rainfall, Double riverLevel, Integer daysWithoutRain,
                         Integer numberOfConflicts, Integer numberOfDisplacedPeople, LocalDateTime observedAt,
                         LocalitiesDTO localities) {
        this.riskType = riskType;
        this.rainfall = rainfall;
        this.riverLevel = riverLevel;
        this.daysWithoutRain = daysWithoutRain;
        this.numberOfConflicts = numberOfConflicts;
        this.numberOfDisplacedPeople = numberOfDisplacedPeople;
        this.observedAt = observedAt;
        this.localities = localities;
    }

    public AlertRiskTypeDTO getRiskType() {
        return riskType;
    }

    public void setRiskType(AlertRiskTypeDTO riskType) {
        this.riskType = riskType;
    }

    public Double getRainfall() {
        return rainfall;
    }

    public void setRainfall(Double rainfall) {
        this.rainfall = rainfall;
    }

    public Double getRiverLevel() {
        return riverLevel;
    }

    public void setRiverLevel(Double riverLevel) {
        this.riverLevel = riverLevel;
    }

    public Integer getDaysWithoutRain() {
        return daysWithoutRain;
    }

    public void setDaysWithoutRain(Integer daysWithoutRain) {
        this.daysWithoutRain = daysWithoutRain;
    }

    public Integer getNumberOfConflicts() {
        return numberOfConflicts;
    }

    public void setNumberOfConflicts(Integer numberOfConflicts) {
        this.numberOfConflicts = numberOfConflicts;
    }

    public Integer getNumberOfDisplacedPeople() {
        return numberOfDisplacedPeople;
    }

    public void setNumberOfDisplacedPeople(Integer numberOfDisplacedPeople) {
        this.numberOfDisplacedPeople = numberOfDisplacedPeople;
    }

    public LocalDateTime getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(LocalDateTime observedAt) {
        this.observedAt = observedAt;
    }

    public LocalitiesDTO getLocalities() {
        return localities;
    }

    public void setLocalities(LocalitiesDTO localities) {
        this.localities = localities;
    }
}

