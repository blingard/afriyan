package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.Communes;
import org.ligot.afriyan.echo.entities.Crpp;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CrppRepository extends JpaRepository<Crpp, UUID> {
    Optional<Crpp> findAllByCommune(Communes communes);

    @Query("SELECT cp.utilisateur FROM Crpp cp WHERE cp.commune.id = :communeId")
    List<Utilisateur> findAllByUserOfCrpp(@Param("communeId") UUID communeId);
}
