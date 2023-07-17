package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAdministrateurRepository extends JpaRepository<Administrateur, Long> {
    Optional<Administrateur> findByEmailAndPwd(String login, String password);
    Optional<Administrateur> findByCode(String code);
}
