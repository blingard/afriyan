package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Status;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class AdministrationDTO extends PersonneDTO{
    private Set<RolesDTO> roles;

    public Set<RolesDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesDTO> roles) {
        this.roles = roles;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public String getCode() {
        return super.getCode();
    }

    @Override
    public void setCode(String code) {
        super.setCode(code);
    }

    @Override
    public String getNom() {
        return super.getNom();
    }

    @Override
    public void setNom(String nom) {
        super.setNom(nom);
    }

    @Override
    public String getPrenom() {
        return super.getPrenom();
    }

    @Override
    public void setPrenom(String prenom) {
        super.setPrenom(prenom);
    }

    @Override
    public String getDdn() {
        return super.getDdn();
    }

    @Override
    public void setDdn(String ddn) {
        super.setDdn(ddn);
    }

    @Override
    public String getLieu() {
        return super.getLieu();
    }

    @Override
    public void setLieu(String lieu) {
        super.setLieu(lieu);
    }

    @Override
    public String getNumero_telephone() {
        return super.getNumero_telephone();
    }

    @Override
    public void setNumero_telephone(String numero_telephone) {
        super.setNumero_telephone(numero_telephone);
    }

    @Override
    public String getPhoto() {
        return super.getPhoto();
    }

    @Override
    public void setPhoto(String photo) {
        super.setPhoto(photo);
    }

    @Override
    public String getLocation() {
        return super.getLocation();
    }

    @Override
    public void setLocation(String location) {
        super.setLocation(location);
    }

    @Override
    public String getAnonymat() {
        return super.getAnonymat();
    }

    @Override
    public void setAnonymat(String anonymat) {
        super.setAnonymat(anonymat);
    }

    @Override
    public String getSexe() {
        return super.getSexe();
    }

    @Override
    public void setSexe(String sexe) {
        super.setSexe(sexe);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
    }
}
