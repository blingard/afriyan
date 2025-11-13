package org.ligot.afriyan.elearning.entities;

import jakarta.persistence.*;
import org.ligot.afriyan.entities.Categories;
import org.ligot.afriyan.sondage.entities.Sondage;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "formations")
public class Formations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String libelle;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Categories categories;

    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<Chapitres> chapitres = new HashSet<>(0);

    @OneToOne()
    private Sondage quizz;
    private String orderChapter;


    private boolean status;

    @Column(columnDefinition = "boolean default false")
    private boolean configure;

    @Column(nullable = false)
    private String author;

    public Formations() {
    }

    public Formations(Long id, String libelle, String description, Categories categories, Set<Chapitres> chapitres, Sondage quizz, String orderChapter, boolean status, boolean configure, String author) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.categories = categories;
        this.chapitres = chapitres;
        this.quizz = quizz;
        this.orderChapter = orderChapter;
        this.status = status;
        this.configure = configure;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Set<Chapitres> getChapitres() {
        return chapitres;
    }

    public void setChapitres(Set<Chapitres> chapitres) {
        this.chapitres = chapitres;
    }

    public Sondage getQuizz() {
        return quizz;
    }

    public void setQuizz(Sondage quizz) {
        this.quizz = quizz;
    }

    public String getOrderChapter() {
        return orderChapter;
    }

    public void setOrderChapter(String orderChapter) {
        this.orderChapter = orderChapter;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isConfigure() {
        return configure;
    }

    public void setConfigure(boolean configure) {
        this.configure = configure;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
