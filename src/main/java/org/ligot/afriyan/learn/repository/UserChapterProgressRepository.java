package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.UserChapterProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserChapterProgressRepository extends JpaRepository<UserChapterProgress, UUID> {

    Optional<UserChapterProgress> findByEnrollmentIdAndChapterId(UUID enrollmentId, UUID chapterId);

    List<UserChapterProgress> findByEnrollmentId(UUID enrollmentId);

    @Query("SELECT COUNT(ucp) FROM UserChapterProgress ucp WHERE ucp.enrollment.id = :enrollmentId AND ucp.chapter.module.id = :moduleId AND ucp.isCompleted = true")
    Long countCompletedChaptersByEnrollmentAndModule(@Param("enrollmentId") UUID enrollmentId, @Param("moduleId") UUID moduleId);

    @Query("SELECT ucp FROM UserChapterProgress ucp WHERE ucp.enrollment.id = :enrollmentId AND ucp.chapter.module.id = :moduleId ORDER BY ucp.chapter.ordre")
    List<UserChapterProgress> findByEnrollmentIdAndModuleId(@Param("enrollmentId") UUID enrollmentId, @Param("moduleId") UUID moduleId);
}
