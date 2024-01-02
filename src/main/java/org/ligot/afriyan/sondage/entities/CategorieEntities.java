package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;
import org.ligot.afriyan.entities.Categorie;

@Entity
@Table(name = "categories")
public class CategorieEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "domain")
    private Categorie domain;

    public CategorieEntities() {
    }

    public CategorieEntities(Long id, Categorie domain) {
        this.id = id;
        this.domain = domain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categorie getDomain() {
        return domain;
    }

    public void setDomain(Categorie domain) {
        this.domain = domain;
    }
}
