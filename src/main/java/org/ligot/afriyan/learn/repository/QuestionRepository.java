package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q WHERE q.quiz.id = :quizId ORDER BY q.ordre ASC")
    List<Question> findByQuizIdOrderByOrdre(@Param("quizId") UUID quizId);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.quiz.id = :quizId")
    Long countByQuizId(@Param("quizId") UUID quizId);

    @Query("SELECT SUM(q.points) FROM Question q WHERE q.quiz.id = :quizId")
    Integer getTotalPointsByQuizId(@Param("quizId") UUID quizId);
}
