package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.sondage.entities.QuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionResponseRepo extends JpaRepository<QuestionResponse, Long> {
    List<QuestionResponse> findQuestionResponsesBySondageId(Long sondage);
}
