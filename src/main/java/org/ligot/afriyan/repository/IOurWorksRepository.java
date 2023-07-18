package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.OurWorks;
import org.ligot.afriyan.entities.OurWorksType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOurWorksRepository extends JpaRepository<OurWorks, Long> {
    List<OurWorks> findAllByStatusTrueAndAndOurWorksType(OurWorksType ourWorksType);
}
