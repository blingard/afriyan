package org.ligot.afriyan.repository;


import org.ligot.afriyan.entities.Denonciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDenonciationRepository extends JpaRepository<Denonciation, Long> {

}
