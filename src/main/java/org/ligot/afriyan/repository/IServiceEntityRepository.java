package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findServiceEntitiesByCentrePartenaire(CentrePartenaire centrePartenaire);
}
