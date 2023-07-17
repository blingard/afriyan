package org.ligot.afriyan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.io.Serializable;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code"),
                @UniqueConstraint(columnNames = "email")
        })
public class Utilisateur extends Personne implements Serializable, Comparable<Utilisateur>{

    @ManyToOne
    private Groupes groupe;
    @Override
    public int compareTo(Utilisateur o) {
        return 0;
    }

    public Groupes getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupes groupe) {
        this.groupe = groupe;
    }
}
