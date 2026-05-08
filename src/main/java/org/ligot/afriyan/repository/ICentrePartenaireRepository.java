package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ICentrePartenaireRepository extends JpaRepository<CentrePartenaire, Long> {
    List<CentrePartenaire> findCentrePartenaireByStatus(Status status);
    Optional<Set<CentrePartenaire>> findCentrePartenaireByCreateurAndStatus(Utilisateur utilisateur, Status status);
    Optional<CentrePartenaire> findCentrePartenaireByTelephone(String telephone);
    Optional<CentrePartenaire> findCentrePartenaireByNom(String nom);
    Optional<CentrePartenaire> findByIdAndStatus(Long id, Status status);
}
