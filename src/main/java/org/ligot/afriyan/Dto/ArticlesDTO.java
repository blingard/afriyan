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

    private TypeDonne type;
    @JsonIgnoreProperties({"administrateur","service"})
    private List<PublicationsDTO> publications = new ArrayList<>();

    public ArticlesDTO() {
    }

    public ArticlesDTO(Long id, String title, String author, String contenu, boolean status, Date date, TypeDonne type, List<PublicationsDTO> publications) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contenu = contenu;
        this.status = status;
        this.date = date;
        this.type = type;
        this.publications = publications;
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

    public List<PublicationsDTO> getPublications() {
        return publications;
    }

    public void setPublications(List<PublicationsDTO> publications) {
        this.publications = publications;
    }

    public TypeDonne getType() {
        return type;
    }

    public void setType(TypeDonne type) {
        this.type = type;
    }
}
