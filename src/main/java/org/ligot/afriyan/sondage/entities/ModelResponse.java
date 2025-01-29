package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "model_response")
public class ModelResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    @Column(columnDefinition = "int default 0")
    private int score;

    public ModelResponse() {
    }

    public ModelResponse(Long id) {
        this.id = id;
    }

    public ModelResponse(Long id, String value, int score) {
        this.id = id;
        this.value = value;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ModelResponse{");
        sb.append("id=").append(id);
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
