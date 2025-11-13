package org.ligot.afriyan.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Menus {
    @Id
    @GeneratedValue
    private UUID id;
    @Column (name = "name", nullable = false, updatable = false)
    private String name;
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean status;

    @Enumerated(EnumType.STRING)
    private FrontType frontType;

    @OneToMany(mappedBy = "rootMenu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Menus> subMenus = new HashSet<>();

    @ManyToOne
    private Menus rootMenu;


    public Menus() {
    }

    public Menus(UUID id, String name, String description, boolean status, FrontType frontType, Set<Menus> subMenus, Menus rootMenu) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.frontType = frontType;
        this.subMenus = subMenus;
        this.rootMenu = rootMenu;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public FrontType getFrontType() {
        return frontType;
    }

    public void setFrontType(FrontType frontType) {
        this.frontType = frontType;
    }

    public Set<Menus> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(Set<Menus> subMenus) {
        this.subMenus = subMenus;
    }

    public Menus getRootMenu() {
        return rootMenu;
    }

    public void setRootMenu(Menus rootMenu) {
        this.rootMenu = rootMenu;
    }
}
