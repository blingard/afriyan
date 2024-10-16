package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.sondage.entities.CategorieEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieEntitiesRepo extends JpaRepository<CategorieEntities, Long> {
    Optional<CategorieEntities> findByDomain(Categorie categorie);
}
