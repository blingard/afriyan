package org.ligot.afriyan.elearning.repo;

import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.sondage.entities.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationsRepo extends JpaRepository<Formations, Long> {
    List<Formations> findFormationsByQuizzIsNotNull();
    Optional<Formations> findFormationsByQuizz(Sondage sondage);
}
