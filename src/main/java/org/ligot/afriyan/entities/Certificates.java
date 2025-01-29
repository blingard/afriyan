package org.ligot.afriyan.entities;

import jakarta.persistence.*;

@Entity
public class Certificates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(length = -1, nullable = false)
    private String contenu;
    private String name;
    private boolean status;

    public Certificates() {
    }

    public Certificates(Long id, String contenu, String name, boolean status) {
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
