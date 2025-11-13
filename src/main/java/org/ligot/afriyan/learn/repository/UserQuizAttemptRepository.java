package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.UserQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserQuizAttemptRepository extends JpaRepository<UserQuizAttempt, UUID> {

    @Query("SELECT a FROM UserQuizAttempt a WHERE a.user.id = :userId AND a.quiz.id = :quizId ORDER BY a.numeroTentative DESC")
    List<UserQuizAttempt> findByUserIdAndQuizIdOrderByNumeroTentativeDesc(
            @Param("userId") Long userId,
            @Param("quizId") UUID quizId);

    @Query("SELECT COUNT(a) FROM UserQuizAttempt a WHERE a.user.id = :userId AND a.quiz.id = :quizId")
    Long countAttemptsByUserIdAndQuizId(
            @Param("userId") Long userId,
            @Param("quizId") UUID quizId);

    @Query("SELECT a FROM UserQuizAttempt a WHERE a.user.id = :userId AND a.quiz.id = :quizId AND a.isPassed = true")
    List<UserQuizAttempt> findPassedAttemptsByUserIdAndQuizId(
            @Param("userId") Long userId,
            @Param("quizId") UUID quizId);

    @Query("SELECT a FROM UserQuizAttempt a WHERE a.enrollment.id = :enrollmentId")
    List<UserQuizAttempt> findByEnrollmentId(@Param("enrollmentId") UUID enrollmentId);

    @Query("SELECT a FROM UserQuizAttempt a WHERE a.user.id = :userId AND a.quiz.id = :quizId " +
           "ORDER BY a.numeroTentative DESC LIMIT 1")
    Optional<UserQuizAttempt> findLatestAttemptByUserIdAndQuizId(
            @Param("userId") Long userId,
            @Param("quizId") UUID quizId);
}
