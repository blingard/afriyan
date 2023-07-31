package org.ligot.afriyan.config.securities;

import org.ligot.afriyan.Dto.LoginRequest;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final IUtilisateurRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl service;

    public AuthService(IUtilisateurRepository repository, JwtService jwtService, AuthenticationManager authenticationManager, UserDetailsServiceImpl service) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    public Object login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        UserDetails use = service.loadUserByUsername(loginRequest.getLogin());
        return jwtService.generateToken(use);
    }
}
