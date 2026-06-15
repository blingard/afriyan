package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.PlanningFamilialDTO;
import org.ligot.afriyan.Dto.PlanningFamilialLogDTO;
import org.ligot.afriyan.service.IPlanningFamilialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlanningFamilialController {

    private final IPlanningFamilialService service;

    public PlanningFamilialController(IPlanningFamilialService service) {
        this.service = service;
    }

    @PostMapping("/user/family-planning")
    @RolesAllowed("USER")
    public ResponseEntity<PlanningFamilialDTO> savePlanning(@Valid @RequestBody PlanningFamilialDTO dto) throws Exception {
        PlanningFamilialDTO saved = service.savePlanning(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/user/family-planning")
    @RolesAllowed("USER")
    public ResponseEntity<PlanningFamilialDTO> getPlanning() throws Exception {
        PlanningFamilialDTO planning = service.getPlanning();
        return ResponseEntity.ok(planning);
    }

    @PostMapping("/user/family-planning/logs")
    @RolesAllowed("USER")
    public ResponseEntity<PlanningFamilialLogDTO> addLog(@Valid @RequestBody PlanningFamilialLogDTO dto) throws Exception {
        PlanningFamilialLogDTO saved = service.addLog(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/user/family-planning/logs")
    @RolesAllowed("USER")
    public ResponseEntity<List<PlanningFamilialLogDTO>> getLogs() throws Exception {
        List<PlanningFamilialLogDTO> logs = service.getLogs();
        return ResponseEntity.ok(logs);
    }
}
