package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.AlertStatus;
import org.ligot.afriyan.echo.entities.AlertRiskType;
import org.ligot.afriyan.echo.entities.Alerts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AlertRepository  extends JpaRepository<Alerts, UUID> {
    Page<Alerts> findAlertsByRiskTypeAndStatus(AlertRiskType riskType, AlertStatus status, Pageable pageable);
    Page<Alerts> findAlertsByRiskType(AlertRiskType riskType, Pageable pageable);
    long countAlertsByRiskType(AlertRiskType riskType);
    long countAlertsByRiskTypeAndStatus(AlertRiskType riskType, AlertStatus status);
}
