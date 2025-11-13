package org.ligot.afriyan.sondage.dto;

import java.util.HashSet;
import java.util.Set;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;

public class QuestionsDTO {
    private Long id;
    private TypeResponse typeResponse;
    private Set<ModelResponseDTO> modelResponses = new HashSet<>(0);
    private String libelle;
    private int position;
    private int score;

    public QuestionsDTO() {
    }

    public QuestionsDTO(Long id) {
        this.id = id;
    }

    public QuestionsDTO(Long id, TypeResponse typeResponse, Set<ModelResponseDTO> modelResponses, String libelle, int position, int score) {
        this.id = id;
        this.typeResponse = typeResponse;
        this.modelResponses = modelResponses;
        this.libelle = libelle;
        this.position = position;
        this.score = score;
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

    public Set<ModelResponseDTO> getModelResponses() {
        return modelResponses;
    }

    public void setModelResponses(Set<ModelResponseDTO> modelResponses) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "QuestionsDTO{" +
                "id=" + id +
                ", typeResponse=" + typeResponse +
                ", modelResponses=" + modelResponses +
                ", libelle='" + libelle + '\'' +
                ", position=" + position +
                ", score=" + score +
                '}';
    }
}
