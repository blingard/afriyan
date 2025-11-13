package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, UUID> {

    @Query("SELECT qo FROM QuestionOption qo WHERE qo.question.id = :questionId ORDER BY qo.ordre ASC")
    List<QuestionOption> findByQuestionIdOrderByOrdre(@Param("questionId") UUID questionId);

    @Query("SELECT qo FROM QuestionOption qo WHERE qo.question.id = :questionId AND qo.isCorrect = true")
    Optional<QuestionOption> findCorrectAnswerByQuestionId(@Param("questionId") UUID questionId);

    @Query("SELECT qo FROM QuestionOption qo WHERE qo.question.id = :questionId AND qo.isCorrect = true")
    List<QuestionOption> findCorrectAnswersByQuestionId(@Param("questionId") UUID questionId);
}
