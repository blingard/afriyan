package org.ligot.afriyan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue
    @Column(name = "IDENTIFANT")
    private long id;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "DESCRIPTION")
    private String description;
}
