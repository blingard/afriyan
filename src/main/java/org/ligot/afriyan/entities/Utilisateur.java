package org.ligot.afriyan.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur extends Personne implements Serializable, Comparable<Utilisateur>{

    @Override
    public int compareTo(Utilisateur o) {
        return 0;
    }
}
