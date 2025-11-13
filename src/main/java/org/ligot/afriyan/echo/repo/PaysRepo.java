package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaysRepo extends JpaRepository<Pays, UUID> {
    Optional<Pays> findPaysByName(String name);
    Optional<Pays> findPaysByAbbr(String abbr);
}

