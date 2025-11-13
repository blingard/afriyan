package org.ligot.afriyan.sondage.dto;

import jakarta.validation.constraints.NotNull;
import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private Set<CategoriesDTO> domain = new HashSet<>(0);
    @NotNull
    private TypeUserSondage typeUser;

    private float scoreTotal;

    public SondageDTO() {
    }

    public SondageDTO(Long id) {
        this.id = id;
    }

    public SondageDTO(Long id, @NotNull String name, String createUser, LocalDateTime createDate, SchedulerDTO scheduler, Set<QuestionsDTO> questions, @NotNull EtatSondage state, @NotNull Set<CategoriesDTO> domain, @NotNull TypeUserSondage typeUser, float scoreTotal) {
        this.id = id;
        this.name = name;
        this.createUser = createUser;
        this.createDate = createDate;
        this.scheduler = scheduler;
        this.questions = questions;
        this.state = state;
        this.domain = domain;
        this.typeUser = typeUser;
        this.scoreTotal = scoreTotal;
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

    public Set<CategoriesDTO> getDomain() {
        return domain;
    }

    public void setDomain(Set<CategoriesDTO> domain) {
        this.domain = domain;
    }

    public TypeUserSondage getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUserSondage typeUser) {
        this.typeUser = typeUser;
    }

    public float getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(float scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
}
