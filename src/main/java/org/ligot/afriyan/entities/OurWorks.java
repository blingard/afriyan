package org.ligot.afriyan.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class OurWorks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(length = -1)
    private String text;
    @Column(length = -1)
    private String photo;
    @Enumerated(EnumType.STRING)
    private OurWorksType ourWorksType;
    private boolean status;

    @OneToMany
    private Set<Articles> articles = new HashSet<>();

    public OurWorks() {
    }

    public OurWorks(Long id) {
        this.id = id;
    }

    public OurWorks(Long id, String title, String text, String photo, OurWorksType ourWorksType, boolean status) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.photo = photo;
        this.ourWorksType = ourWorksType;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public OurWorksType getOurWorksType() {
        return ourWorksType;
    }

    public void setOurWorksType(OurWorksType ourWorksType) {
        this.ourWorksType = ourWorksType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Articles> getArticles() {
        return articles;
    }

    public void setArticles(Set<Articles> articles) {
        this.articles = articles;
    }
}
