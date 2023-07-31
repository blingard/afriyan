package org.ligot.afriyan.entities;

import jakarta.persistence.*;

@Entity
public class Missions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(length = -1)
    private String description;
    @Column(length = -1)
    private String phote;
    private boolean status = false;

    public Missions(Long id) {
        this.id = id;
    }

    public Missions() {
    }

    public Missions(Long id, String title, String description, String phote, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.phote = phote;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhote() {
        return phote;
    }

    public void setPhote(String phote) {
        this.phote = phote;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
