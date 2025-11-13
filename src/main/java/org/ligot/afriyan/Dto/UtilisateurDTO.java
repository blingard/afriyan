package org.ligot.afriyan.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import org.ligot.afriyan.echo.dto.CommunesDTO;
import org.ligot.afriyan.entities.Sexe;
import org.ligot.afriyan.entities.Status;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UtilisateurDTO extends PersonneDTO{


    @JsonIgnoreProperties({"roles","utilisateurs"})
    private GroupesDTO groupe;
    private CommunesDTO communes;

    public UtilisateurDTO(Long id, String code, String nom, String prenom, Date ddn, String lieu, String telephone, String photo, String location, String anonymat, Sexe sexe, @Email String email, Status status, String pwd, GroupesDTO groupe, List<String> phoneNumbers, CommunesDTO communes) {
        super(id, code, nom, prenom, ddn, lieu, telephone, photo, location, anonymat, sexe, email, status, pwd, phoneNumbers);
        this.groupe = groupe;
        this.communes = communes;
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
    public String getTelephone() {
        return super.getTelephone();
    }

    @Override
    public void setTelephone(String telephone) {
        super.setTelephone(telephone);
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

    public CommunesDTO getCommunes() {
        return communes;
    }

    public void setCommunes(CommunesDTO communes) {
        this.communes = communes;
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

    @Override
    public void setPhoneNumbers(List<String> phoneNumbers) {
        super.setPhoneNumbers(phoneNumbers);
    }
}
