package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Entity
public class ThemeTraiter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (unique = true, nullable = false)
    private String title;
    private boolean active;

    public ThemeTraiter() {
    }

    public ThemeTraiter(Long id, String title, boolean active) {
        this.id = id;
        this.title = title;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
