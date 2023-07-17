package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByNom(String nom);
}
