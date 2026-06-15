package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.PlanningFamilial;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IPlanningFamilialRepository extends JpaRepository<PlanningFamilial, Long> {
    Optional<PlanningFamilial> findByUtilisateur(Utilisateur utilisateur);
    Optional<PlanningFamilial> findByUtilisateur_Id(Long userId);
    Optional<PlanningFamilial> findByUtilisateur_Uuid(String uuid);
}
