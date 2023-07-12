package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProduitRepository extends JpaRepository<Produit, Long> {
}
