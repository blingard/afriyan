package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    Optional<Quiz> findByModuleId(UUID moduleId);

    Optional<Quiz> findByFormationId(UUID formationId);

    @Query("SELECT q FROM Quiz q WHERE q.type = :type")
    Optional<Quiz> findByType(@Param("type") Quiz.QuizType type);
}
