package org.ligot.afriyan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @GeneratedValue
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(name = "LIBELLE", unique = true, nullable = false)
    private String libelle;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column (name = "DATE_CREATION")
    private Date dateCreation;
}
