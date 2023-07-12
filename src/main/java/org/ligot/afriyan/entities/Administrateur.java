package org.ligot.afriyan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Administrateur extends Personne implements Serializable, Comparable<Administrateur>{

    @OneToMany
    @JoinColumn(name = "ROLES", referencedColumnName = "IDENTIFIANT")
    private Set<Roles> roles= new HashSet<>();
    @Override
    public int compareTo(Administrateur o) {
        return 0;
    }
}
