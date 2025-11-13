package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.Communes;
import org.ligot.afriyan.echo.entities.Departement;
import org.ligot.afriyan.echo.entities.Localities;
import org.ligot.afriyan.echo.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CommuneRepo extends JpaRepository<Communes, UUID> {
    Optional<Communes> findCommunesByName(String name);
    List<Communes> findAllByDepartement_Id(UUID uuid);
    List<Communes> findByNameStartingWith(String prefix);

    @Query(
            value = """
                SELECT * 
                FROM communes c 
                WHERE unaccent(LOWER(c.name)) LIKE unaccent(LOWER(CONCAT(:prefix, '%')))
                """,
            nativeQuery = true
    )
    List<Communes> findByNameStartingWithIgnoreCase(@Param("prefix") String prefix);
}

