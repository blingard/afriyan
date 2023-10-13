package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Mediatech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMediatechRepository extends JpaRepository<Mediatech, Long> {
    List<Mediatech> findAllByActiveTrue();
}
