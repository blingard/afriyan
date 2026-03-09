package org.ligot.afriyan.implement;

import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilsService {
    private final IUtilisateurRepository repository;

    public UtilsService(IUtilisateurRepository repository) {
        this.repository = repository;
    }


    public Utilisateur getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByUuid(username).orElseThrow(()->new RuntimeException("User with id = "+username+" don't exist"));
    }


    public Utilisateur getUserById(Long id) {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new RuntimeException("User with id = "+id+" don't exist");
        return utilisateur;
    }
}
