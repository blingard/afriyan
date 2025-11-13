package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.Communes;
import org.ligot.afriyan.echo.entities.Maire;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaireRepo extends JpaRepository<Maire, UUID> {
    Optional<Maire> findAllByUtilisateur(Utilisateur utilisateur);
    Optional<Maire> findAllByCommune(Communes communes);
    List<Maire> findAllByCommuneAndActive(Communes communes, boolean active);
}

