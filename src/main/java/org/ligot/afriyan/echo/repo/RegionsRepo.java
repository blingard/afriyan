package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegionsRepo extends JpaRepository<Region, UUID> {
    Optional<Region> findRegionByName(String name);
}

