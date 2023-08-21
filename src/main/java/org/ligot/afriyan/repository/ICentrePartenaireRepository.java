package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICentrePartenaireRepository extends JpaRepository<CentrePartenaire, Long> {
    List<CentrePartenaire> findCentrePartenaireByStatus(Status status);
    CentrePartenaire findCentrePartenaireByCreateur(Utilisateur utilisateur);
}
