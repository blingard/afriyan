package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByCode(String code);
    Optional<Utilisateur> findByEmail(String eMail);

    @Query("SELECT u FROM Utilisateur u WHERE u.numero_telephone =: num")
    Utilisateur findByNumero_telephone(@Param("num") String phone);
    List<Utilisateur> findByGroupe(Groupes groupes);
     Utilisateur findByNom(String nom);
}
