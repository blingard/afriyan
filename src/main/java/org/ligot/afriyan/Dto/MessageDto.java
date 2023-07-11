package org.ligot.afriyan.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import org.ligot.afriyan.entities.Administrateur;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.Collection;

public class MessageDto {

    private String objects;
    private String corps;
}
