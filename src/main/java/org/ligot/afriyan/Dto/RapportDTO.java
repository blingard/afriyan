package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Status;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
public class RapportDTO {

    private Long id;
    private String entete;
    private String cotenu;
    private Date date;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntete() {
        return entete;
    }

    public void setEntete(String entete) {
        this.entete = entete;
    }

    public String getCotenu() {
        return cotenu;
    }

    public void setCotenu(String cotenu) {
        this.cotenu = cotenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
