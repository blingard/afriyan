package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.PeriodLog;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IPeriodLogRepository extends JpaRepository<PeriodLog, Long> {
    List<PeriodLog> findByUtilisateurOrderByDateDebutDesc(Utilisateur utilisateur);
    List<PeriodLog> findByUtilisateur_IdOrderByDateDebutDesc(Long userId);
    List<PeriodLog> findByUtilisateur_UuidOrderByDateDebutDesc(String uuid);
}
