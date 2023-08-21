package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Articles;
import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.entities.TypeDonne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArticlesRepository extends JpaRepository<Articles, Long> {
    List<Articles> findAllByStatusTrueAndTypeDonne(TypeDonne typeDonne);
    List<Articles> findAllByTypeDonne(TypeDonne typeDonne);
    List<Articles> findAllByTypeDonneAndCategorieAndStatusTrue(TypeDonne typeDonne, Categorie categorie);
    Page<Articles> findAllByTypeDonne(TypeDonne typeDonne, Pageable pageable);
}
