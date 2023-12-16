package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;

@Entity
@Table(name = "sondages")
@AllArgsConstructor
public class Sondage {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String createUser;
    private LocalDateTime createDate;

    @OneToOne
    private Scheduler scheduler;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Questions> questions = new HashSet<>(0);

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EtatSondage state;

    @Enumerated(EnumType.STRING)
    @Column(name = "domain")
    private Categorie domain;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_user")
    private TypeUserSondage typeUser;

    public Sondage() {
    }

    public Sondage(Long id) {
        this.id = id;
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

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Set<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Questions> questions) {
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