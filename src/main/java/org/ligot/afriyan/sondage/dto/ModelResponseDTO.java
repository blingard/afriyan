package org.ligot.afriyan.sondage.dto;

public class ModelResponseDTO {
    private Long id;

    private String value;

    public ModelResponseDTO() {
    }

    public ModelResponseDTO(Long id) {
        this.id = id;
    }

    public ModelResponseDTO(Long id, String value) {
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
