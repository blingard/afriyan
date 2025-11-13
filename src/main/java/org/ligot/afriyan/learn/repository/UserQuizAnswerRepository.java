package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.UserQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserQuizAnswerRepository extends JpaRepository<UserQuizAnswer, UUID> {

    @Query("SELECT a FROM UserQuizAnswer a WHERE a.attempt.id = :attemptId")
    List<UserQuizAnswer> findByAttemptId(@Param("attemptId") UUID attemptId);

    @Query("SELECT a FROM UserQuizAnswer a WHERE a.attempt.id = :attemptId AND a.question.id = :questionId")
    Optional<UserQuizAnswer> findByAttemptIdAndQuestionId(
            @Param("attemptId") UUID attemptId,
            @Param("questionId") UUID questionId);

    @Query("SELECT COUNT(a) FROM UserQuizAnswer a WHERE a.attempt.id = :attemptId AND a.isCorrect = true")
    Long countCorrectAnswersByAttemptId(@Param("attemptId") UUID attemptId);

    @Query("SELECT SUM(a.pointsObtenus) FROM UserQuizAnswer a WHERE a.attempt.id = :attemptId")
    Integer getTotalPointsByAttemptId(@Param("attemptId") UUID attemptId);
}
