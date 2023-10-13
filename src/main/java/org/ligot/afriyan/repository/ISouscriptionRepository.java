package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.ServiceEntity;
import org.ligot.afriyan.entities.Souscription;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Provider;
import java.util.List;
import java.util.Optional;

public interface ISouscriptionRepository extends JpaRepository<Souscription, Long> {
    List<Souscription> findAllByUtilisateur(Utilisateur utilisateur);
    Optional<Souscription> findAllByUtilisateurAndStatus(Utilisateur utilisateur, Status status);
    List<Souscription> findAllByServiceOrderByDatecreationDesc(ServiceEntity service);
    List<Souscription> findAllByServiceAndStatusOrderByDatecreationDesc(ServiceEntity service, Status status);
}
