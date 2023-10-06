package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface IGroupesRepository extends JpaRepository<Groupes, Long> {
    Optional<Groupes> findByName(String name);
    Groupes findByUtilisateursContains(Utilisateur users);
    List<Groupes> findByRolesContaining(Roles roles);
}
