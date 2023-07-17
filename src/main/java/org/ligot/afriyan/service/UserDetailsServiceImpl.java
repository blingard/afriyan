package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.UserDetailsImpl;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.repository.IGroupesRepository;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUtilisateurRepository repository;
    private final IGroupesRepository groupesRepository;

    public UserDetailsServiceImpl(IUtilisateurRepository repository, IGroupesRepository groupesRepository) {
        this.repository = repository;
        this.groupesRepository = groupesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = repository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User with username "+username+" don't exist"));
        Set<Roles> roles = groupesRepository.findByUtilisateursContains(user).getRoles();
        return UserDetailsImpl.build(user,roles);
    }
}
