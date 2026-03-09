package org.ligot.afriyan.config.securities;

import org.ligot.afriyan.Dto.LoginRequest;
import org.ligot.afriyan.Dto.UserDetailsImpl;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.service.IUtilisateur;
import org.ligot.afriyan.service.KeycloakService;
import org.ligot.afriyan.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final IUtilisateur repository;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl service;

    public AuthService(IUtilisateur repository, JwtService jwtService, UserDetailsServiceImpl service) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.service = service;
    }

    public Map<String, Object> login(LoginRequest loginRequest) {
        return service.loadUserByUsername(loginRequest.getLogin().trim(), loginRequest.getPassword().trim());
    }

    public Map<String, Object> refresh(String token) {
        return service.refreshToken(token);
    }

    public UtilisateurDTO getUtilisateurByLogin(String login) throws Exception {
        return repository.login(login);
    }

    public Utilisateur getUtilisateurByLoginForgetPwd(String login) throws Exception {
        return repository.loginForgetPwd(login);
    }
}
