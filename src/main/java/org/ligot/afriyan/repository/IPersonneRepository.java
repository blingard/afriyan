package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonneRepository extends JpaRepository<Personne, Long> {
}
