package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.LoginRequest;
import org.ligot.afriyan.config.securities.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class SecurityController {
    private final AuthService service;

    public SecurityController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        HttpHeaders  httpHeaders = new HttpHeaders();
        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE2OTAyNTExODEsImV4cCI6MTcyMTc4NzE4MSwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6IlsnVVNFUicsJ1ZJU0lUT1InLCdST09UJywnU1VQRVJBRE1JTicsJ0FETUlOJ10ifQ.ruVSnE66cHjCFo1tB2CzCdixglSXwqsThWPptobH4EU";
        httpHeaders.add("Authorization", "Bearer "+service.login(loginRequest)/*token*/);
        //return ResponseEntity.ok(service.login(loginRequest));
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }

}
