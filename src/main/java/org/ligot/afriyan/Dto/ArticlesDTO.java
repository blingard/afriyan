package org.ligot.afriyan.Dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import org.ligot.afriyan.entities.TypeDonne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticlesDTO {
    private Long id;
    private String title;
    private String author;
    private String contenu;
    private boolean status;
    private Date date;
    private String phote;
    private TypeDonne typeDonne;

    public ArticlesDTO() {
    }

    public ArticlesDTO(Long id, String title, String author, String contenu, boolean status, Date date, TypeDonne type, PublicationsDTO publication) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contenu = contenu;
        this.status = status;
        this.date = date;
        this.typeDonne = type;
    }

    public ArticlesDTO(Long id) {
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContenu() {
        return contenu;
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

    public TypeDonne getTypeDonne() {
        return typeDonne;
    }

    public void setTypeDonne(TypeDonne typeDonne) {
        this.typeDonne = typeDonne;
    }

    public String getPhote() {
        return phote;
    }

    public void setPhote(String phote) {
        this.phote = phote;
    }
}
