package org.ligot.afriyan.elearning.repo;

import org.ligot.afriyan.elearning.entities.HistoriquesLearning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoriquesLearningRepo extends JpaRepository<HistoriquesLearning, Long> {
    List<HistoriquesLearning> findByUserIdAndFormationId(Long userId, Long formationId);
    Optional<HistoriquesLearning> findByUserIdAndFormationIdAndChapitreId(Long userId, Long formationId, Long chapterId);
}
