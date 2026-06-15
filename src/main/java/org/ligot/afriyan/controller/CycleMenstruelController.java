package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.CycleMenstruelSettingsDTO;
import org.ligot.afriyan.Dto.CycleProjectionDTO;
import org.ligot.afriyan.Dto.PeriodLogDTO;
import org.ligot.afriyan.Dto.SimulationRequestDTO;
import org.ligot.afriyan.service.ICycleMenstruelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class CycleMenstruelController {

    private final ICycleMenstruelService service;
    
    // Rate limiter pour l'API publique
    private final ConcurrentHashMap<String, List<Long>> rateLimits = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 5; // 5 requêtes maximum
    private static final long TIME_WINDOW_MS = 60 * 1000; // par minute (60 000 ms)

    public CycleMenstruelController(ICycleMenstruelService service) {
        this.service = service;
    }

    private void checkRateLimit(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        long now = System.currentTimeMillis();
        rateLimits.compute(ip, (key, timestamps) -> {
            if (timestamps == null) {
                timestamps = new ArrayList<>();
            }
            // Retirer les requêtes expirées de la fenêtre de temps
            timestamps.removeIf(t -> now - t > TIME_WINDOW_MS);
            if (timestamps.size() >= MAX_REQUESTS) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, 
                        "Trop de requêtes. Veuillez réessayer dans une minute.");
            }
            timestamps.add(now);
            return timestamps;
        });
    }

    @PostMapping("/public/menstrual-cycle/simulate")
    public ResponseEntity<List<CycleProjectionDTO>> simulate(@RequestBody SimulationRequestDTO dto, HttpServletRequest request) {
        checkRateLimit(request);
        List<CycleProjectionDTO> projections = service.simulateCycle(dto);
        return ResponseEntity.ok(projections);
    }

    @PostMapping("/user/menstrual-cycle/settings")
    @RolesAllowed("USER")
    public ResponseEntity<CycleMenstruelSettingsDTO> saveSettings(@Valid @RequestBody CycleMenstruelSettingsDTO dto) throws Exception {
        CycleMenstruelSettingsDTO saved = service.saveSettings(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/user/menstrual-cycle/settings")
    @RolesAllowed("USER")
    public ResponseEntity<CycleMenstruelSettingsDTO> getSettings() throws Exception {
        CycleMenstruelSettingsDTO settings = service.getSettings();
        return ResponseEntity.ok(settings);
    }

    @PostMapping("/user/menstrual-cycle/logs")
    @RolesAllowed("USER")
    public ResponseEntity<PeriodLogDTO> addPeriodLog(@Valid @RequestBody PeriodLogDTO dto) throws Exception {
        PeriodLogDTO saved = service.addPeriodLog(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/user/menstrual-cycle/logs")
    @RolesAllowed("USER")
    public ResponseEntity<List<PeriodLogDTO>> getPeriodLogs() throws Exception {
        List<PeriodLogDTO> logs = service.getPeriodLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/user/menstrual-cycle/projections")
    @RolesAllowed("USER")
    public ResponseEntity<List<CycleProjectionDTO>> getProjections(@RequestParam(name = "nbCycles", defaultValue = "3") int nbCycles) throws Exception {
        List<CycleProjectionDTO> projections = service.getProjections(nbCycles);
        return ResponseEntity.ok(projections);
    }
}
