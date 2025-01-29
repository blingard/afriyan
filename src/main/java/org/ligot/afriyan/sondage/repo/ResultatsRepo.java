package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.sondage.entities.Resultats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultatsRepo extends JpaRepository<Resultats, Long> {

    List<Resultats> findAllByUtilisateurAndFormation(Utilisateur utilisateur, Formations formations);
    Optional<Resultats> findAllByUtilisateurAndFormationAndStatusTrue(Utilisateur utilisateur, Formations formations);

    long countByUtilisateurAndFormationAndStatusTrue(Utilisateur utilisateur, Formations formations);

}
