package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface DepartementsRepo extends JpaRepository<Departement, UUID> {
    Optional<Departement> findDepartementsByName(String name);
    List<Departement> findDepartementsByRegion_Id(UUID id);
}

