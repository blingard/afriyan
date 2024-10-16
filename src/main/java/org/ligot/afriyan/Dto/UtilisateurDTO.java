package org.ligot.afriyan.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import org.ligot.afriyan.entities.Sexe;
import org.ligot.afriyan.entities.Status;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UtilisateurDTO extends PersonneDTO{


    @JsonIgnoreProperties({"roles","utilisateurs"})
    private GroupesDTO groupe;

    public UtilisateurDTO(Long id, String code, String nom, String prenom, Date ddn, String lieu, String numero_telephone, String photo, String location, String anonymat, Sexe sexe, @Email String email, Status status, String pwd, GroupesDTO groupe) {
        super(id, code, nom, prenom, ddn, lieu, numero_telephone, photo, location, anonymat, sexe, email, status, pwd);
        this.groupe = groupe;
    }

    public UtilisateurDTO(GroupesDTO groupe) {
        this.groupe = groupe;
    }

    public UtilisateurDTO() {
    }

    public UtilisateurDTO(Long id) {
        super(id);
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

    public GroupesDTO getGroupe() {
        return groupe;
    }

    public void setGroupe(GroupesDTO groupe) {
        this.groupe = groupe;
    }

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
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
    public boolean getIsFirstConnexion() {
        return super.getIsFirstConnexion();
    }

    @Override
    public void setIsFirstConnexion(boolean firstConnexion) {
        super.setIsFirstConnexion(firstConnexion);
    }
}
