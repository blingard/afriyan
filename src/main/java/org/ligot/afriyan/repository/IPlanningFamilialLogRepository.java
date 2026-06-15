package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.PlanningFamilial;
import org.ligot.afriyan.entities.PlanningFamilialLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IPlanningFamilialLogRepository extends JpaRepository<PlanningFamilialLog, Long> {
    List<PlanningFamilialLog> findByPlanningFamilialOrderByDateLogDesc(PlanningFamilial planningFamilial);
    List<PlanningFamilialLog> findByPlanningFamilial_Utilisateur_UuidOrderByDateLogDesc(String uuid);
}
