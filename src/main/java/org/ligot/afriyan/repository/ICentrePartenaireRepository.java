package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICentrePartenaireRepository extends JpaRepository<CentrePartenaire, Long> {
    List<CentrePartenaire> findCentrePartenaireByStatus(Status status);
    Optional<CentrePartenaire> findCentrePartenaireByCreateur(Utilisateur utilisateur);
    Optional<CentrePartenaire> findCentrePartenaireByTelephone(String telephone);
    Optional<CentrePartenaire> findCentrePartenaireByNom(String nom);
}
