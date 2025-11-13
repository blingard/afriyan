package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, UUID> {

    @Query("SELECT p FROM UserProgress p WHERE p.enrollment.id = :enrollmentId")
    List<UserProgress> findByEnrollmentId(@Param("enrollmentId") UUID enrollmentId);

    @Query("SELECT p FROM UserProgress p WHERE p.enrollment.id = :enrollmentId AND p.module.id = :moduleId")
    Optional<UserProgress> findByEnrollmentIdAndModuleId(
            @Param("enrollmentId") UUID enrollmentId,
            @Param("moduleId") UUID moduleId);

    @Query("SELECT p FROM UserProgress p WHERE p.enrollment.user.id = :userId AND p.module.id = :moduleId")
    Optional<UserProgress> findByUserIdAndModuleId(
            @Param("userId") Long userId,
            @Param("moduleId") UUID moduleId);

    @Query("SELECT COUNT(p) FROM UserProgress p WHERE p.enrollment.id = :enrollmentId AND p.status = 'COMPLETED'")
    Long countCompletedModulesByEnrollmentId(@Param("enrollmentId") UUID enrollmentId);

    @Query("SELECT AVG(p.progressionPourcent) FROM UserProgress p WHERE p.enrollment.id = :enrollmentId")
    Double getAverageProgressByEnrollmentId(@Param("enrollmentId") UUID enrollmentId);
}
