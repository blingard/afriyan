package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.LoginRequest;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.config.securities.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class SecurityController {
    private final AuthService service;

    public SecurityController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        HttpHeaders  httpHeaders = new HttpHeaders();
        Map<String, Object> data = new HashMap<>();
        String accessToken = "Bearer "+service.login(loginRequest);
        data.put("accessToken", accessToken);
        UtilisateurDTO utilisateur = service.getUtilisateurByLogin(loginRequest.getLogin());
        utilisateur.getGroupe().getRoles().clear();
        data.put("user", utilisateur);
        String refreshToken = "Bearer "+service.refreshToken(utilisateur.getId());
        data.put("refreshToken", refreshToken);
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }

}
