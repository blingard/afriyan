package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Sexe;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.init.PermissionEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query("""
       SELECT DISTINCT u
       FROM Utilisateur u
       LEFT JOIN u.permissionsAdd pa
       LEFT JOIN u.permissionsRemove pr
       LEFT JOIN u.groupe g
       LEFT JOIN g.permissions gp
       WHERE 
            (pa = :permission OR gp = :permission)
       AND
            (pr IS NULL OR pr <> :permission)
       """)
    Set<Utilisateur> findUsersWithEffectivePermission(@Param("permission") PermissionEnum permission);

    Optional<Utilisateur> findByCode(String code);
    Optional<Utilisateur> findByEmail(String eMail);
    Optional<Utilisateur> findByUuid(String eMail);

    @Query("SELECT DISTINCT EXTRACT(YEAR FROM u.dCreation) FROM Utilisateur u WHERE u.dCreation IS NOT NULL ORDER BY EXTRACT(YEAR FROM u.dCreation) ASC")
    List<Integer> findDistinctYears();

    @Query("SELECT u FROM Utilisateur u WHERE u.telephone = ?1")
    Utilisateur findByNumero_telephone(String phone);
    @Query("SELECT u FROM Utilisateur u WHERE u.telephone = ?1")
    Optional<Utilisateur> findByTelephone(String phone);

    @Query("SELECT u FROM Utilisateur u WHERE u.telephone LIKE ?1%")
    Page<Utilisateur> findUsersByPhoneNumberStartingWith(String numero, Pageable pageable);

/*    @Query("SELECT EXTRACT(YEAR FROM u.dCreation) AS year, " +
            "       EXTRACT(MONTH FROM u.dCreation) AS month, " +
            "       COUNT(u) AS total " +
            "FROM Utilisateur u " +
            "GROUP BY EXTRACT(YEAR FROM u.dCreation), EXTRACT(MONTH FROM u.dCreation) " +
            "ORDER BY year, month")*/
@Query("SELECT EXTRACT(YEAR FROM u.dCreation) AS year, " +
        "       EXTRACT(MONTH FROM u.dCreation) AS month, " +
        "       COUNT(u) AS total " +
        "FROM Utilisateur u " +
        "WHERE u.dCreation IS NOT NULL " +
        "GROUP BY EXTRACT(YEAR FROM u.dCreation), EXTRACT(MONTH FROM u.dCreation) " +
        "ORDER BY year, month")
    List<Object[]> getMonthlyUserCreationStatistics();
@Query("SELECT EXTRACT(MONTH FROM u.dCreation) AS month, " +
        "       COUNT(u) AS total " +
        "FROM Utilisateur u " +
        "WHERE EXTRACT(YEAR FROM u.dCreation) = ?1 " +
        "GROUP BY EXTRACT(YEAR FROM u.dCreation), EXTRACT(MONTH FROM u.dCreation) " +
        "ORDER BY month")
    List<Object[]> getMonthlyUserCreationStatistics(int year);
    List<Utilisateur> findByGroupe(Groupes groupes);

    List<Utilisateur> findByGroupe_Roles_NomAndNomStartingWith(String roleName, String name);
    List<Utilisateur> findByGroupe_Roles_NomAndTelephoneStartingWith(String roleName, String name);
    Page<Utilisateur> findByGroupe(Groupes groupes, Pageable pageable);
     Utilisateur findByNom(String nom);

     long countUtilisateursBySexe(Sexe sexe);

     Page<Utilisateur> findAllByCommunes_IdAndStatus(UUID communesId, Status status, Pageable pageable);
}
