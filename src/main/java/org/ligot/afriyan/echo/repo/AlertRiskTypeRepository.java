package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.AlertRiskType;
import org.ligot.afriyan.echo.entities.Alerts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlertRiskTypeRepository extends JpaRepository<AlertRiskType, UUID> {
    Optional<AlertRiskType> findAllById(UUID id);
    Optional<AlertRiskType> findAllByCode(String id);
    List<AlertRiskType> findAllByState(boolean state);
    Page<AlertRiskType> findAll(Pageable pageable);
}
