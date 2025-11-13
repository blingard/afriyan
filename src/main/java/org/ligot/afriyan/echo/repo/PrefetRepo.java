package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.Departement;
import org.ligot.afriyan.echo.entities.Prefet;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrefetRepo extends JpaRepository<Prefet, UUID> {
    Optional<Prefet> findAllByUtilisateur(Utilisateur utilisateur);
    List<Prefet> findAllByDepartementAndActive(Departement departement, boolean active);
}

