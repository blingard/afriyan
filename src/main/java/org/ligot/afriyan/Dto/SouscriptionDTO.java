package org.ligot.afriyan.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Status;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class SouscriptionDTO {
    private Long id;
    @JsonIgnoreProperties({"groupe"})
    private UtilisateurDTO utilisateur;
    @JsonIgnoreProperties({"dateCreation"})
    private ServiceDTO service;
    private Date datecreation;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ServiceDTO getService() {
        return service;
    }

    public void setService(ServiceDTO service) {
        this.service = service;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SouscriptionDTO{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", service=" + service +
                ", datecreation=" + datecreation +
                ", status=" + status +
                '}';
    }
}
