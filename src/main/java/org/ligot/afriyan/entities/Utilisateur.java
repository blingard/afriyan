package org.ligot.afriyan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur extends Personne implements Serializable, Comparable<Utilisateur>{

    @ManyToOne
    private Groupes groupe;
    @Override
    public int compareTo(Utilisateur o) {
        return 0;
    }
}
