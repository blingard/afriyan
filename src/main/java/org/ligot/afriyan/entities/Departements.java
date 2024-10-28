package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Departements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String chefLieux;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Arrondissements> arrondissements=new HashSet<>(0);

    public Departements() {
    }

    public Departements(Long id, String name, String chefLieux, Set<Arrondissements> arrondissements) {
        this.id = id;
        this.name = name;
        this.chefLieux = chefLieux;
        this.arrondissements = arrondissements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChefLieux() {
        return chefLieux;
    }

    public void setChefLieux(String chefLieux) {
        this.chefLieux = chefLieux;
    }

    public Set<Arrondissements> getArrondissements() {
        return arrondissements;
    }

    public void setArrondissements(Set<Arrondissements> arrondissements) {
        this.arrondissements = arrondissements;
    }
}
