package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Regions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String chefLieux;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Departements> departements = new HashSet<>(0);

    public Regions() {
    }

    public Regions(Long id, String name, String chefLieux, Set<Departements> departements) {
        this.id = id;
        this.name = name;
        this.chefLieux = chefLieux;
        this.departements = departements;
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

    public Set<Departements> getDepartements() {
        return departements;
    }

    public void setDepartements(Set<Departements> departements) {
        this.departements = departements;
    }
}
