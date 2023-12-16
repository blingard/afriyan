package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.sondage.entities.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepo extends JpaRepository<Questions, Long> {}
