package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Entity
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column (name = "TITLE", unique = true, nullable = false)
    private String title;
    private String author;
    @Column(length = -1)
    private String contenu;
    @Column(length = -1)
    private String resumer;
    private String phote;
    private boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categories categories;
    @CurrentTimestamp
    private Date date;
    private TypeDonne typeDonne;
    private int lue;

    public Articles(Long id, String title, String author, String contenu, String resumer, String phote, boolean status, Categories categories, Date date, TypeDonne typeDonne, int lue, Publications publication) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contenu = contenu;
        this.resumer = resumer;
        this.phote = phote;
        this.status = status;
        this.categories = categories;
        this.date = date;
        this.typeDonne = typeDonne;
        this.lue = lue;
        this.publication = publication;
    }

    public int getLue() {
        return lue;
    }

    public void setLue(int lue) {
        this.lue = lue;
    }

    public TypeDonne getType() {
        return typeDonne;
    }
    public void setType(TypeDonne type) {
        this.typeDonne = type;
    }
    public Publications getPublication() {
        return publication;
    }
    public void setPublication(Publications publication) {
        this.publication = publication;
    }
    @ManyToOne
    private Publications publication;
    public Articles() {
    }
    public Articles(Long id) {
        this.id = id;
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
    public String getAuthor() {
        return author;
    }
    public String getPhote() {
        return phote;
    }
    public void setPhote(String phote) {
        this.phote = phote;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContenu() {
        return contenu;
    }
    public TypeDonne getTypeDonne() {
        return typeDonne;
    }
    public void setTypeDonne(TypeDonne typeDonne) {
        this.typeDonne = typeDonne;
    }
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getResumer() {
        return resumer;
    }

    public void setResumer(String resumer) {
        this.resumer = resumer;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Publications getPublications() {
        return publication;
    }
    public void setPublications(Publications publication) {
        this.publication = publication;
    }
    public Categories getCategories() {
        return categories;
    }
    public void setCategories(Categories categories) {
        this.categories = categories;
    }

}
