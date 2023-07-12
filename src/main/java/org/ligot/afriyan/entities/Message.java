package org.ligot.afriyan.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;

    @Column(name = "OBJECTS")
    private String objects;

    @Column(name = "CORPS")
    private String corps;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Utilisateur utilisateur;

}
