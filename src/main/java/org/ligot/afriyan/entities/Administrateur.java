package org.ligot.afriyan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Administrateur extends Personne implements Serializable, Comparable<Administrateur>{

    @OneToMany
    private Set<Roles> roles= new HashSet<>();
    @Override
    public int compareTo(Administrateur o) {
        return code.compareTo(o.code);
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
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
    public Date getDdn() {
        return super.getDdn();
    }

    @Override
    public void setDdn(Date ddn) {
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
    public Sexe getSexe() {
        return super.getSexe();
    }

    @Override
    public void setSexe(Sexe sexe) {
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
    public String getPwd() {
        return super.getPwd();
    }

    @Override
    public void setPwd(String pwd) {
        super.setPwd(pwd);
    }

    @Override
    public Date getdCreation() {
        return super.getdCreation();
    }

    @Override
    public void setdCreation(Date dCreation) {
        super.setdCreation(dCreation);
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "roles=" + roles +
                ", id=" + id +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ddn=" + ddn +
                ", lieu='" + lieu + '\'' +
                ", numero_telephone='" + numero_telephone + '\'' +
                ", photo='" + photo + '\'' +
                ", location='" + location + '\'' +
                ", anonymat='" + anonymat + '\'' +
                ", sexe=" + sexe +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", dCreation=" + dCreation +
                '}';
    }
}
