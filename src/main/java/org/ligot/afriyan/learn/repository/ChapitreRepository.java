package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.Chapitres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChapitreRepository extends JpaRepository<Chapitres, UUID> {

    List<Chapitres> findByModuleIdOrderByOrdre(UUID moduleId);

    @Query("SELECT COUNT(c) FROM LearnChapitres c WHERE c.module.id = :moduleId")
    Long countByModuleId(@Param("moduleId") UUID moduleId);
}
