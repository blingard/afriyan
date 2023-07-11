package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
}
