package org.ligot.afriyan.sondage.dto;

public class ModelResponseDTO {
    private Long id;

    private String value;

    private int score;

    public ModelResponseDTO() {
    }

    public ModelResponseDTO(Long id) {
        this.id = id;
    }

    public ModelResponseDTO(Long id, String value, int score) {
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
        final StringBuffer sb = new StringBuffer("ModelResponseDTO{");
        sb.append("id=").append(id);
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
