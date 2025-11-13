package org.ligot.afriyan.learn.repository;

import org.ligot.afriyan.learn.entities.UserFormationEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserFormationEnrollmentRepository extends JpaRepository<UserFormationEnrollment, UUID> {

    @Query("SELECT e FROM UserFormationEnrollment e WHERE e.user.id = :userId")
    List<UserFormationEnrollment> findByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM UserFormationEnrollment e WHERE e.formation.id = :formationId")
    List<UserFormationEnrollment> findByFormationId(@Param("formationId") UUID formationId);

    @Query("SELECT e FROM UserFormationEnrollment e WHERE e.user.id = :userId AND e.formation.id = :formationId")
    Optional<UserFormationEnrollment> findByUserIdAndFormationId(
            @Param("userId") Long userId,
            @Param("formationId") UUID formationId);

    @Query("SELECT e FROM UserFormationEnrollment e WHERE e.user.id = :userId AND e.status = :status")
    List<UserFormationEnrollment> findByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") UserFormationEnrollment.EnrollmentStatus status);

    @Query("SELECT COUNT(e) FROM UserFormationEnrollment e WHERE e.formation.id = :formationId AND e.status = 'COMPLETED'")
    Long countCompletedByFormationId(@Param("formationId") UUID formationId);

    @Query("SELECT COUNT(e) FROM UserFormationEnrollment e WHERE e.user.id = :userId AND e.status = 'COMPLETED'")
    Long countCompletedByUserId(@Param("userId") Long userId);
}
