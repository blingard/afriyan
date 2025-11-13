package org.ligot.afriyan.Dto;

import org.ligot.afriyan.entities.FrontType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MenusDTO {
    private UUID id;
    private String name;
    private String description;

    private boolean status;

    private FrontType frontType;

    private Set<MenusDTO> subMenus = new HashSet<>();

    private MenusDTO rootMenu;


    public MenusDTO() {
    }

    public MenusDTO(UUID id, String name, String description, boolean status, FrontType frontType, Set<MenusDTO> subMenus, MenusDTO rootMenu) {
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

    public Set<MenusDTO> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(Set<MenusDTO> subMenus) {
        this.subMenus = subMenus;
    }

    public MenusDTO getRootMenu() {
        return rootMenu;
    }

    public void setRootMenu(MenusDTO rootMenu) {
        this.rootMenu = rootMenu;
    }

    @Override
    public String toString() {
        return "MenusDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", frontType=" + frontType +
                ", subMenus=" + subMenus +
                ", rootMenu=" + rootMenu +
                '}';
    }
}
