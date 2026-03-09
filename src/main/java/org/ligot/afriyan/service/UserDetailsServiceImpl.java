package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.config.securities.UserAuthRecord;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl {
    private final IUtilisateurRepository repository;

    private final KeycloakService keycloakService;
    private final IUtilisateur iUtilisateur;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(IUtilisateurRepository repository, KeycloakService keycloakService, IUtilisateur iUtilisateur, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.keycloakService = keycloakService;
        this.iUtilisateur = iUtilisateur;
        this.passwordEncoder = passwordEncoder;
    }


    public Map<String, Object> loadUserByUsername(String username, String pwd) {
        username = username.trim();
        Optional<Utilisateur> user = repository.findByEmail(username);
        if (user.isEmpty()) {
            user = repository.findByCode(username);
            if (user.isEmpty()) {
                user = repository.findByTelephone(username);
                throw new UsernameNotFoundException("User with username " + username + " don't exist");
            }

        }
        if (user.get().getStatus().equals(Status.INACTIVE))
            throw new UsernameNotFoundException(
                    "User with username " + username + " is disable please contact administrator");
        Utilisateur utilisateur = user.get();
        if(user.get().getUuid()==null) {
            if(passwordEncoder.matches(pwd, user.get().getPwd())==false)
                throw new UsernameNotFoundException("User with username pwd " + username + " don't exist");
            String uuid = keycloakService.createUser(utilisateur.getEmail()==null ? utilisateur.getCode() : utilisateur.getEmail(),
                    utilisateur.getEmail(), pwd, utilisateur.getPrenom(), utilisateur.getNom(), true);
            utilisateur.setUuid(uuid);
            repository.save(utilisateur);

        }
        Map<String, Object> returnData = keycloakService.login(username, pwd);
        UtilisateurDTO utilisateurDTO = iUtilisateur.login(username);
        returnData.put("user", utilisateurDTO);
        returnData.put("permissions", utilisateur.getEffectivePermission());

        return returnData;
    }

    public Map<String, Object> refreshToken(String refreshToken){
        return keycloakService.refreshToken(refreshToken);
    }
}
