package org.ligot.afriyan.Dto;


import jakarta.validation.constraints.NotNull;

public class CertificatesDTO {
    private Long id;

    @NotNull(message = "content cannot be null")
    private String contenu;
    private String name;
    private boolean status;

    public CertificatesDTO() {
    }

    public CertificatesDTO(Long id, @NotNull(message = "content cannot be null") String contenu, String name, boolean status) {
        this.id = id;
        this.contenu = contenu;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
