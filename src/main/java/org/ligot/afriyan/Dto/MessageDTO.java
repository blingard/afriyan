package org.ligot.afriyan.Dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.ligot.afriyan.entities.Status;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    @NotNull
    private String objects;
    @NotNull
    private String corps;
    @NotNull
    private Status status;
    private Set<String> contacts = new HashSet<>();
    @JsonIgnoreProperties({"groupe"})
    private UtilisateurDTO utilisateur;

   
    public String getObjects() {
        return objects;
    }

    public void setObjects(String objects) {
        this.objects = objects;
    }

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public Set<String> getContacts() {
        return contacts;
    }

    public void setContacts(Set<String> contacts) {
        this.contacts = contacts;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }
}
