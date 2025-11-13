package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.Formation;
import org.ligot.afriyan.learn.enumerations.FormationLevel;
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
public interface FormationRepository extends JpaRepository<Formation, UUID> {

    List<Formation> findByStatus(Formation.FormationStatus status);

    Page<Formation> findByStatus(Formation.FormationStatus status, Pageable pageable);

    @Query("SELECT f FROM Formation f WHERE f.status = 'PUBLISHED' ORDER BY f.dateCreation DESC")
    List<Formation> findPublishedFormations();

    @Query("SELECT f FROM Formation f WHERE f.status = 'PUBLISHED' ORDER BY f.dateCreation DESC")
    Page<Formation> findPublishedFormations(Pageable pageable);

    @Query("SELECT f FROM Formation f WHERE f.createdBy = :userId")
    List<Formation> findByCreatedBy(@Param("userId") Long userId);

    @Query("SELECT f FROM Formation f WHERE f.createdBy = :userId")
    Page<Formation> findByCreatedBy(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT f FROM Formation f WHERE LOWER(f.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(f.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Formation> searchFormations(@Param("keyword") String keyword);

    @Query("SELECT f FROM Formation f WHERE LOWER(f.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(f.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Formation> searchFormations(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT f FROM Formation f WHERE f.niveau = :niveau AND f.status = 'PUBLISHED'")
    List<Formation> findByNiveauAndPublished(@Param("niveau") FormationLevel niveau);

    @Query("SELECT f FROM Formation f WHERE f.niveau = :niveau AND f.status = 'PUBLISHED'")
    Page<Formation> findByNiveauAndPublished(@Param("niveau") FormationLevel niveau, Pageable pageable);
}
