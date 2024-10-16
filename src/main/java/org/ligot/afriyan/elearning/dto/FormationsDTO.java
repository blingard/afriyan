package org.ligot.afriyan.elearning.dto;

import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.sondage.dto.SondageDTO;
import org.ligot.afriyan.sondage.entities.Sondage;

import java.util.HashSet;
import java.util.Set;

public class FormationsDTO {
    private Long id;

    private String libelle;

    private String description;

    private Categorie categorie;

    private Set<ChapitresDTO> chapitres = new HashSet<>(0);

    private SondageDTO quizz;
    private String orderChapter;

    private boolean status;
    private boolean configure;

    private String author;

    public FormationsDTO() {
    }


    public FormationsDTO(Long id, String libelle, String description, Categorie categorie, Set<ChapitresDTO> chapitres, SondageDTO quizz, String orderChapter, boolean status, boolean configure, String author) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.categorie = categorie;
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Set<ChapitresDTO> getChapitres() {
        return chapitres;
    }

    public void setChapitres(Set<ChapitresDTO> chapitres) {
        this.chapitres = chapitres;
    }

    public SondageDTO getQuizz() {
        return quizz;
    }

    public void setQuizz(SondageDTO quizz) {
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
