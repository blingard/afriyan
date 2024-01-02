package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.sondage.entities.CategorieEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieEntitiesRepo extends JpaRepository<CategorieEntities, Long> {}
