package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Carrousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarrouselRepository extends JpaRepository<Carrousel, Long> {
}
