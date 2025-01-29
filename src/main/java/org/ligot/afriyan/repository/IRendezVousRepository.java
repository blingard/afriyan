package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.RendezVous;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRendezVousRepository extends JpaRepository<RendezVous, Long> {

    List<RendezVous> findRendezVousByUtilisateur_Id(Long id);
    List<RendezVous> findRendezVousByAndCentrePartenaire_Id(Long id);

    Optional<RendezVous> findByIdAndUtilisateur_Id(Long id, Long userId);
}
