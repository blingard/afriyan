package org.ligot.afriyan.partenaire.repository;

import org.ligot.afriyan.partenaire.entities.Partenaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire, UUID> {

    Optional<Partenaire> findByNom(String nom);

    List<Partenaire> findByStatut(Boolean statut);

    Page<Partenaire> findByStatut(Boolean statut, Pageable pageable);

    List<Partenaire> findByPublish(Boolean publish);

    Page<Partenaire> findByPublish(Boolean publish, Pageable pageable);

    @Query("SELECT p FROM Partenaire p WHERE p.statut = true AND p.publish = true")
    List<Partenaire> findActiveAndPublished();

    @Query("SELECT p FROM Partenaire p WHERE p.statut = true AND p.publish = true")
    Page<Partenaire> findActiveAndPublished(Pageable pageable);

    @Query("SELECT p FROM Partenaire p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Partenaire> searchPartenaires(@Param("keyword") String keyword);

    @Query("SELECT p FROM Partenaire p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Partenaire> searchPartenaires(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByNom(String nom);
}
