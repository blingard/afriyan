package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.CommuneComity;
import org.ligot.afriyan.echo.entities.Localities;
import org.ligot.afriyan.echo.entities.Prefet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CommunityComityRepo extends JpaRepository<CommuneComity, UUID> {
    Optional<CommuneComity> findAllByLocality(Localities localities);
}

