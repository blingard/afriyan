package org.ligot.afriyan.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.*;

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
    private String phote;
    private boolean status;

    @Enumerated(EnumType.STRING)
    private Categorie categorie;
    @CurrentTimestamp
    private Date date;
    @Enumerated(EnumType.STRING)
    private TypeDonne typeDonne;
    private int lue;

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
    public Articles(Long id, String title, String author, String contenu, boolean status, Date date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contenu = contenu;
        this.status = status;
        this.date = date;
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
    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

}
