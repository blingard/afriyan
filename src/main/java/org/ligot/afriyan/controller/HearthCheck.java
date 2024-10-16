package org.ligot.afriyan.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HearthCheck {

    private final ApplicationContext context;

    public HearthCheck(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping("/test/healthckeck")
    public String healthCheck(){
        return "HealtChecck";
    }

}
