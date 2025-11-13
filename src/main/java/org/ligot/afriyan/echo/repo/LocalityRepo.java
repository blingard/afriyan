package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.Localities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface LocalityRepo extends JpaRepository<Localities, UUID> {
    Optional<Localities> findByNameAndCommune_Id(String name, UUID communeId);
    List<Localities> findByNameStartingWith(String prefix);
    List<Localities> findByNameStartingWithIgnoreCase(String prefix);
}

