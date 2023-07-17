package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Valeurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IValeursRepository extends JpaRepository<Valeurs, Long> {
    List<Valeurs> findAllByStatusTrue();
}
