package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Missions;
import org.ligot.afriyan.entities.TypeDonne;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMissionsRepository extends JpaRepository<Missions, Long> {
    List<Missions> findAllByStatusTrue();
    List<Missions> findTop4ByStatusTrue(Sort sort);
}
