package org.ligot.afriyan.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Setter
@Getter
public class Administrateur extends Personne implements Serializable, Comparable<Administrateur>{

    @Override
    public int compareTo(Administrateur o) {
        return 0;
    }
}
