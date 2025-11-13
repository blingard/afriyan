package org.ligot.afriyan.echo.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.echo.dto.AlertRiskTypeDTO;
import org.ligot.afriyan.echo.service.AlertRiskTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/alert-type")
public class AlertTypeController {
    private final AlertRiskTypeService service;

    public AlertTypeController(AlertRiskTypeService service) {
        this.service = service;
    }

    @PostMapping("create")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void create(@Valid @RequestBody AlertRiskTypeDTO alertRiskTypeDTO) throws Exception {
        service.save(alertRiskTypeDTO);
    }

    @GetMapping
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<PageDTO<AlertRiskTypeDTO>> listByPage(@RequestParam(name = "page", defaultValue = "0")int page) {
        return ResponseEntity.ok(service.getAllByPage(page));
    }

    @GetMapping("user/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","CCPR_COMMITTEE","LOCAL_AUTHORITY","MAIRE","COMMUNITY_COMMITTEE"})
    public ResponseEntity<AlertRiskTypeDTO> byId(@PathVariable(name = "id", required = true)String id) {
        return ResponseEntity.ok(service.findActiveById(id));
    }

    @GetMapping("admin/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public ResponseEntity<AlertRiskTypeDTO> byIdAdmin(@PathVariable(name = "id", required = true)String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("admin/active-list")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","CCPR_COMMITTEE","LOCAL_AUTHORITY","MAIRE","COMMUNITY_COMMITTEE"})
    public ResponseEntity<List<AlertRiskTypeDTO>> getAllActive() {
        return ResponseEntity.ok(service.getAllActive());
    }

    @PutMapping("/update/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void update(@PathVariable("id") String id, @RequestBody @Valid AlertRiskTypeDTO alertRiskTypeDTO) {
        service.update(alertRiskTypeDTO, id);
    }

    @PutMapping("/enable/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void enable(@PathVariable("id") String id) {
        service.active(id);
    }

}
