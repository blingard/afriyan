package org.ligot.afriyan.elearning.entities;

import jakarta.persistence.*;
import org.ligot.afriyan.entities.Categorie;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "formations_user")
public class FormationsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long formationId;

    @Column()
    private Long note;

    private boolean finish;
    public FormationsUser() {
    }

    public FormationsUser(Long id, Long userId, Long formationId, boolean finish, Long note) {
        this.id = id;
        this.userId = userId;
        this.formationId = formationId;
        this.finish = finish;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFormationId() {
        return formationId;
    }

    public void setFormationId(Long formationId) {
        this.formationId = formationId;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Long getNote() {
        return note;
    }

    public void setNote(Long note) {
        this.note = note;
    }
}
