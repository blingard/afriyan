package org.ligot.afriyan.elearning.repo;

import org.ligot.afriyan.elearning.entities.FormationsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormationUserRepo extends JpaRepository<FormationsUser, Long> {
    List<FormationsUser> findByUserId(Long userId);
    List<FormationsUser> findByUserIdAndFinishIsTrue(Long userId);
    List<FormationsUser> findByUserIdAndFinishIsFalse(Long userId);
    Optional<FormationsUser> findByUserIdAndFormationId(Long userId, Long formationId);
    boolean existsByUserIdAndFormationId(Long userId, Long formationId);
}
