package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.sondage.entities.ModelResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelResponseRepo extends JpaRepository<ModelResponse, Long> {}
