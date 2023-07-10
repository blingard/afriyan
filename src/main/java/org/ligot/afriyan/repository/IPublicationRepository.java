package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Publications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPublicationRepository extends JpaRepository<Publications, Long> {
}
