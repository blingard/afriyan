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
public class AlertTypeController {
    private final AlertRiskTypeService service;

    public AlertTypeController(AlertRiskTypeService service) {
        this.service = service;
    }

    @PostMapping("api/alert-type/create")
    @RolesAllowed(value = {"CREATE_ALERT_TYPE"})
    public void create(@Valid @RequestBody AlertRiskTypeDTO alertRiskTypeDTO) throws Exception {
        service.save(alertRiskTypeDTO);
    }

    @GetMapping("api/alert-type")
    @RolesAllowed(value = {"GET_ALERT_TYPE"})
    public ResponseEntity<PageDTO<AlertRiskTypeDTO>> listByPage(@RequestParam(name = "page", defaultValue = "0")int page) {
        return ResponseEntity.ok(service.getAllByPage(page));
    }

    @GetMapping("api/alert-type/user/{id}")
    @RolesAllowed(value = {"GET_ALERT_TYPE"})
    public ResponseEntity<AlertRiskTypeDTO> byId(@PathVariable(name = "id", required = true)String id) {
        return ResponseEntity.ok(service.findActiveById(id));
    }

    @GetMapping("api/alert-type/admin/{id}")
    @RolesAllowed(value = {"GET_ALERT_TYPE_ADMIN"})
    public ResponseEntity<AlertRiskTypeDTO> byIdAdmin(@PathVariable(name = "id", required = true)String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("public/alert-type/active-list")
    public ResponseEntity<List<AlertRiskTypeDTO>> getAllActivePublic() {
        return ResponseEntity.ok(service.getAllActivePublic());
    }

    @GetMapping("admin/active-list")
    @RolesAllowed(value = {"GET_ALERT_TYPE"})
    public ResponseEntity<List<AlertRiskTypeDTO>> getAllActive() {
        return ResponseEntity.ok(service.getAllActive());
    }

    @PutMapping("api/alert-type/update/{id}")
    @RolesAllowed(value = {"UPDATE_ALERT_TYPE"})
    public void update(@PathVariable("id") String id, @RequestBody @Valid AlertRiskTypeDTO alertRiskTypeDTO) {
        service.update(alertRiskTypeDTO, id);
    }

    @PutMapping("api/alert-type/enable/{id}")
    @RolesAllowed(value = {"UPDATE_ALERT_TYPE"})
    public void enable(@PathVariable("id") String id) {
        service.active(id);
    }

}
