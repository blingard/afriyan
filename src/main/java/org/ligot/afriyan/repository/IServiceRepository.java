package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<Service, Long> {
}
