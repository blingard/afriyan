package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "model_response")
public class ModelResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    public ModelResponse() {
    }

    public ModelResponse(Long id) {
        this.id = id;
    }

    public ModelResponse(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
