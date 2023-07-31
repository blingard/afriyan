package org.ligot.afriyan.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(unique = true)
    private String title;
    private String author;
    @Column(length = -1)
    private String contenu;
    @Column(length = -1)
    private String phote;
    private boolean status;
    private Date date;

    private TypeDonne typeDonne;


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
}
