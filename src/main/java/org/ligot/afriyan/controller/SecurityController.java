package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.ForgetPasswordRequest;
import org.ligot.afriyan.Dto.LoginRequest;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.config.securities.AuthService;
import org.ligot.afriyan.entities.ForgetPassword;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.implement.TwilioService;
import org.ligot.afriyan.repository.IForgetPasswordRepository;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.ligot.afriyan.implement.Utils.*;

import java.sql.Date;
import java.time.Instant;
import java.util.*;

@RestController
public class SecurityController {
    private final AuthService service;
    private final IUtilisateur iUtilisateur;
    private final IForgetPasswordRepository iForgetPasswordRepository;
    private final TwilioService twilioService;

    public SecurityController(AuthService service, IUtilisateur iUtilisateur, IForgetPasswordRepository iForgetPasswordRepository, TwilioService twilioService) {
        this.service = service;
        this.iUtilisateur = iUtilisateur;
        this.iForgetPasswordRepository = iForgetPasswordRepository;
        this.twilioService = twilioService;
    }

    @PostMapping("/public/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        System.err.println("ELLA");
        HttpHeaders  httpHeaders = new HttpHeaders();
        System.err.println("ELLA 1");
        Map<String, Object> data = new HashMap<>();
        System.err.println("ELLA");
        String accessToken = "Bearer "+service.login(loginRequest);
        System.err.println("ELLA 2");
        data.put("accessToken", accessToken);
        System.err.println("ELLA");
        UtilisateurDTO utilisateur = service.getUtilisateurByLogin(loginRequest.getLogin().trim());
        System.err.println("ELLA 3");
        utilisateur.getGroupe().getRoles().clear();
        System.err.println("ELLA");
        data.put("user", utilisateur);
        System.err.println("ELLA 4");
        String refreshToken = "Bearer "+service.refreshToken(utilisateur.getId());
        System.err.println("ELLA");
        data.put("refreshToken", refreshToken);
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("public/api/auth/forget_password/confirm")
    public void forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) throws Exception {
        iUtilisateur.forgetPassword(forgetPasswordRequest);
    }

    @GetMapping("public/api/auth/forget_password")
    public void forgetPasswordGetUser(@RequestParam(name = "login", required = true) String login) throws Exception {
        Utilisateur utilisateurDTO = service.getUtilisateurByLoginForgetPwd(login.trim());
        String pwd = genCodeNum();
        String message = "Mot de passe oublie \n";
        message = message+"";
        message = message+" \n code de reinitialisation:"+pwd;
        twilioService.sendOneSms(utilisateurDTO.getTelephone().trim(),message);
        ForgetPassword forgetPassword = new ForgetPassword(
                null,
                utilisateurDTO.getTelephone().trim(),
                true,
                pwd,
                Date.from(Instant.now())
        );
        iForgetPasswordRepository.save(forgetPassword);
    }

}
