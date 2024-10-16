package org.ligot.afriyan.elearning.repo;

import org.ligot.afriyan.elearning.entities.FormationsQuizz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormationQuizzRepo extends JpaRepository<FormationsQuizz, Long> {
    List<FormationsQuizz> findByQuizzId(Long quizzId);
    Optional<FormationsQuizz> findByFormationId(Long formationId);
    Optional<FormationsQuizz> findByQuizzIdAndFormationId(Long quizzId, Long formationId);
    boolean existsByQuizzIdAndFormationId(Long quizzId, Long formationId);
}
