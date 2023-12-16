package org.ligot.afriyan.sondage.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;

public class SondageDTO {
    private Long id;
    @NotNull
    private String name;
    private String createUser;
    private LocalDateTime createDate;
    private SchedulerDTO scheduler;
    private Set<QuestionsDTO> questions = new HashSet<>(0);
    @NotNull
    private EtatSondage state;
    @NotNull
    private Categorie domain;
    @NotNull
    private TypeUserSondage typeUser;

    public SondageDTO() {
    }

    public SondageDTO(Long id) {
        this.id = id;
    }

    public SondageDTO(Long id, String name, String createUser, LocalDateTime createDate, SchedulerDTO scheduler, Set<QuestionsDTO> questions, EtatSondage state, Categorie domain, TypeUserSondage typeUser) {
        this.id = id;
        this.name = name;
        this.createUser = createUser;
        this.createDate = createDate;
        this.scheduler = scheduler;
        this.questions = questions;
        this.state = state;
        this.domain = domain;
        this.typeUser = typeUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public SchedulerDTO getScheduler() {
        return scheduler;
    }

    public void setScheduler(SchedulerDTO scheduler) {
        this.scheduler = scheduler;
    }

    public Set<QuestionsDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionsDTO> questions) {
        this.questions = questions;
    }

    public EtatSondage getState() {
        return state;
    }

    public void setState(EtatSondage state) {
        this.state = state;
    }

    public Categorie getDomain() {
        return domain;
    }

    public void setDomain(Categorie domain) {
        this.domain = domain;
    }

    public TypeUserSondage getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUserSondage typeUser) {
        this.typeUser = typeUser;
    }
}
