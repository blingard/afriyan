package org.ligot.afriyan.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.ligot.afriyan.echo.entities.Communes;
import org.ligot.afriyan.echo.entities.Localities;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import org.ligot.afriyan.init.PermissionEnum;

@Entity
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "code"),
        @UniqueConstraint(columnNames = "email")
})
public class Utilisateur implements Serializable, Comparable<Utilisateur> {

    @Id
    @Column(name = "IDENTIFIANT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "CODE", nullable = false, unique = true)
    protected String code;

    @Column(name = "NOM")
    protected String nom;

    @Column(name = "PRENOM")
    protected String prenom;

    @Column(name = "DATE_NAISSANCE")
    protected Date ddn;

    @Column(name = "LIEU_NAISSANCE")
    protected String lieu;

    @Column(name = "telephone", unique = true)
    protected String telephone;

    protected String uuid;

    @ElementCollection
    @CollectionTable(name = "user_phone_numbers", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone_number")
    private List<String> phoneNumbers;

    @Column(name = "PHOTO")
    protected String photo;

    @Column(name = "LOCATION")
    protected String location;

    @Column(name = "ANONYMAT")
    protected String anonymat;

    @Column(name = "SEXE")
    @Enumerated(EnumType.STRING)
    protected Sexe sexe;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    protected Status status = Status.ACTIVE;

    @Column(name = "EMAIL")
    @Size(max = 50)
    protected String email;

    @Column(name = "PASSWORD", nullable = false)
    protected String pwd;

    @Column(name = "DATECREATION")
    @CurrentTimestamp
    protected Date dCreation;

    @Column(name = "firstconnexion")
    private boolean isFirstConnexion = true;

    @ManyToOne(fetch = FetchType.EAGER)
    private Groupes groupe;

    @ManyToOne(fetch = FetchType.EAGER)
    private Communes communes;

    @ElementCollection(targetClass = PermissionEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_permissions_add", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "permission_add", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<PermissionEnum> permissionsAdd = new HashSet<>();

    @ElementCollection(targetClass = PermissionEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_permissions_remove", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "permission_remove", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<PermissionEnum> permissionsRemove = new HashSet<>();

    public Utilisateur(Long id) {
        this.id = id;
    }

    public Utilisateur() {
    }

    @Override
    public int compareTo(Utilisateur o) {
        return 0;
    }

    public Groupes getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupes groupe) {
        this.groupe = groupe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDdn() {
        return ddn;
    }

    public void setDdn(Date ddn) {
        this.ddn = ddn;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAnonymat() {
        return anonymat;
    }

    public void setAnonymat(String anonymat) {
        this.anonymat = anonymat;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getdCreation() {
        return dCreation;
    }

    public void setdCreation(Date dCreation) {
        this.dCreation = dCreation;
    }

    public boolean getIsFirstConnexion() {
        return isFirstConnexion;
    }

    public void setIsFirstConnexion(boolean firstConnexion) {
        isFirstConnexion = firstConnexion;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Communes getCommunes() {
        return communes;
    }

    public void setCommunes(Communes communes) {
        this.communes = communes;
    }

    public Set<PermissionEnum> getPermissionAdd() {
        return permissionsAdd;
    }

    public void setPermissionAdd(Set<PermissionEnum> permissionsAdd) {
        this.permissionsAdd = permissionsAdd;
    }

    public Set<PermissionEnum> getPermissionRemove() {
        return permissionsRemove;
    }

    public void setPermissionRemove(Set<PermissionEnum> permissionsRemove) {
        this.permissionsRemove = permissionsRemove;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<PermissionEnum> getPermissionsAdd() {
        return permissionsAdd;
    }

    public void setPermissionsAdd(Set<PermissionEnum> permissionsAdd) {
        this.permissionsAdd = permissionsAdd;
    }

    public Set<PermissionEnum> getPermissionsRemove() {
        return permissionsRemove;
    }

    public void setPermissionsRemove(Set<PermissionEnum> permissionsRemove) {
        this.permissionsRemove = permissionsRemove;
    }

    public Set<PermissionEnum> getEffectivePermission(){
        Set<PermissionEnum> effectivePermissions = new HashSet<>(groupe.getPermissions());
        effectivePermissions.addAll(permissionsAdd);
        effectivePermissions.removeAll(permissionsRemove);
        return effectivePermissions;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ddn=" + ddn +
                ", lieu='" + lieu + '\'' +
                ", telephone='" + telephone + '\'' +
                ", photo='" + photo + '\'' +
                ", location='" + location + '\'' +
                ", anonymat='" + anonymat + '\'' +
                ", sexe=" + sexe +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", dCreation=" + dCreation +
                ", groupe=" + groupe +
                '}';
    }
}
