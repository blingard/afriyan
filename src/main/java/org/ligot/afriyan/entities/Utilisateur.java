package org.ligot.afriyan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur extends Personne implements Serializable, Comparable<Utilisateur>{

    @OneToMany
    @JoinColumn(name = "ROLES", referencedColumnName = "IDENTIFIANT")
    private Set<Roles> roles= new HashSet<>();
    @Override
    public int compareTo(Utilisateur o) {
        return 0;
    }
}
