package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Modules, UUID> {

    @Query("SELECT m FROM Modules m WHERE m.formation.id = :formationId ORDER BY m.ordre ASC")
    List<Modules> findByFormationIdOrderByOrdre(@Param("formationId") UUID formationId);

    @Query("SELECT COUNT(m) FROM Modules m WHERE m.formation.id = :formationId")
    Long countByFormationId(@Param("formationId") UUID formationId);

    @Query("SELECT m FROM Modules m WHERE m.formation.id = :formationId AND m.withQuiz = true")
    List<Modules> findModulesWithQuizByFormationId(@Param("formationId") UUID formationId);
}
