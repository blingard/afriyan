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
@RequestMapping("api/auth")
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        HttpHeaders  httpHeaders = new HttpHeaders();
        Map<String, Object> data = new HashMap<>();
        String accessToken = "Bearer "+service.login(loginRequest);
        data.put("accessToken", accessToken);
        UtilisateurDTO utilisateur = service.getUtilisateurByLogin(loginRequest.getLogin().trim());
        utilisateur.getGroupe().getRoles().clear();
        data.put("user", utilisateur);
        String refreshToken = "Bearer "+service.refreshToken(utilisateur.getId());
        data.put("refreshToken", refreshToken);
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/forget_password/confirm")
    public void forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) throws Exception {
        iUtilisateur.forgetPassword(forgetPasswordRequest);
    }

    @GetMapping("/forget_password")
    public void forgetPasswordGetUser(@RequestParam(name = "login", required = true) String login) throws Exception {
        Utilisateur utilisateurDTO = service.getUtilisateurByLoginForgetPwd(login.trim());
        String pwd = genCodeNum();
        String message = "Mot de passe oublie \n";
        message = message+"";
        message = message+" \n code de reinitialisation:"+pwd;
        twilioService.sendOneSm(utilisateurDTO.getNumero_telephone().trim(),message);
        ForgetPassword forgetPassword = new ForgetPassword(
                null,
                utilisateurDTO.getNumero_telephone().trim(),
                true,
                pwd,
                Date.from(Instant.now())
        );
        iForgetPasswordRepository.save(forgetPassword);
    }

}
