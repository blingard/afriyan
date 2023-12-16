package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;

@Entity
@Table(name = "questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_response")
    private TypeResponse typeResponse;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Answer> values = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER)
    private Set<ModelResponse> modelResponses = new HashSet<>(0);
    private String libelle;
    private int position;

    public Questions() {

    }

    public Questions(Long id) {
        this.id = id;
    }

    public Questions(Long id, TypeResponse typeResponse, Set<Answer> values, Set<ModelResponse> modelResponses, String libelle, int position) {
        this.id = id;
        this.typeResponse = typeResponse;
        this.values = values;
        this.modelResponses = modelResponses;
        this.libelle = libelle;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeResponse getTypeResponse() {
        return typeResponse;
    }

    public void setTypeResponse(TypeResponse typeResponse) {
        this.typeResponse = typeResponse;
    }

    public Set<Answer> getValues() {
        return values;
    }

    public void setValues(Set<Answer> values) {
        this.values = values;
    }

    public Set<ModelResponse> getModelResponses() {
        return modelResponses;
    }

    public void setModelResponses(Set<ModelResponse> modelResponses) {
        this.modelResponses = modelResponses;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
