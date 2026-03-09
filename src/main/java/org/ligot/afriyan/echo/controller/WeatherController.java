package org.ligot.afriyan.echo.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.echo.WeatherTime;
import org.ligot.afriyan.echo.dto.WeatherPrevisionDTO;
import org.ligot.afriyan.echo.dto.WeatherRecord;
import org.ligot.afriyan.echo.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }


    @GetMapping("api/weather/list")
    @RolesAllowed(value = {"GET_WEATHER"})
    public Set<WeatherRecord> listAll(@RequestParam(name = "date", required = false)String date,
                                      @RequestParam(name = "dep", required = false)String dep,
                                      @RequestParam(name = "weatherTime", required = true) WeatherTime weatherTime) throws Exception {
        LocalDate today = LocalDate.now();
        if(date!=null){
            today =LocalDate.parse(date);
        }
        return service.getWeathers(today, weatherTime, dep);
    }

}
