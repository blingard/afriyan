package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.sondage.entities.Sondage;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SondageRepo extends JpaRepository<Sondage, Long> {
    List<Sondage> findSondagesByState(EtatSondage state);
}
