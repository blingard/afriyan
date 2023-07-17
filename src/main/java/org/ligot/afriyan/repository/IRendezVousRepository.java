package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.RendezVous;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRendezVousRepository extends JpaRepository<RendezVous, Long> {

    List<RendezVous> findRendezVousByUtilisateur_Id(Long id);

}
