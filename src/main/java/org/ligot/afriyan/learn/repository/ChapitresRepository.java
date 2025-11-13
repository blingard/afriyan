package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.Chapitres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChapitresRepository extends JpaRepository<Chapitres, UUID> {

    @Query("SELECT c FROM LearnChapitres c WHERE c.module.id = :moduleId ORDER BY c.ordre ASC")
    List<Chapitres> findByModuleIdOrderByOrdre(@Param("moduleId") UUID moduleId);

    @Query("SELECT COUNT(c) FROM LearnChapitres c WHERE c.module.id = :moduleId")
    Long countByModuleId(@Param("moduleId") UUID moduleId);
}
