package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.CycleMenstruelSettings;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ICycleMenstruelSettingsRepository extends JpaRepository<CycleMenstruelSettings, Long> {
    Optional<CycleMenstruelSettings> findByUtilisateur(Utilisateur utilisateur);
    Optional<CycleMenstruelSettings> findByUtilisateur_Id(Long userId);
    Optional<CycleMenstruelSettings> findByUtilisateur_Uuid(String uuid);
}
