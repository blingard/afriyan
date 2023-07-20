package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByCode(String code);
    Optional<Utilisateur> findByEmail(String eMail);
    Page<Utilisateur> findByGroupe(Groupes groupes, Pageable pageable);
     Utilisateur findByNom(String nom);
}
