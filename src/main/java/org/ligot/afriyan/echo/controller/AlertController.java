package org.ligot.afriyan.echo.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.echo.dto.AlertUserDto;
import org.ligot.afriyan.echo.dto.AlertsDTO;
import org.ligot.afriyan.echo.service.AlertValidationService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AlertController {
    private final AlertValidationService service;

    public AlertController(AlertValidationService service) {
        this.service = service;
    }

    @PostMapping("api/alert/create")
    @RolesAllowed(value = {"CREATE_ALERT"})
    public void submitAlert(@Valid @RequestBody AlertsDTO alerts) throws Exception {
        service.submitAlert(alerts);
    }

    @PostMapping("api/alert/validate/{id}")
    @RolesAllowed(value = {"VALIDATE_ALERT"})
    public void validate(@RequestParam(name = "approve") boolean approved,
                         @RequestParam(name = "comment") String comment,
                         @PathVariable(name = "id") String id) throws Exception {
        service.validateAlert(UUID.fromString(id), approved, comment);
    }

    @GetMapping("api/alert/get")
    @RolesAllowed(value = {"GET_ALERT"})
    public AlertUserDto getAlertById(@RequestParam(name = "id") String id) throws Exception {
        return service.getAlert(UUID.fromString(id));
    }

    @GetMapping("api/alert/list")
    @RolesAllowed(value = {"GET_ALERT"})
    public PageDTO<AlertsDTO> listAll(
            @RequestParam(name = "type") String idRiskType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) throws Exception {
        return service.listAll(page, size, idRiskType);
    }

    @GetMapping("public/alert/list")
    public PageDTO<AlertsDTO> listAllPublic(
            @RequestParam(name = "type") String idRiskType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) throws Exception {
        return service.listAllPublic(page, size, idRiskType);
    }

    @GetMapping("api/alert/count")
    @RolesAllowed(value = {"GET_ALERT"})
    public long count(@RequestParam(name = "type") String type) throws Exception {
        return service.count(type);
    }

    @GetMapping("public/alert/count")
    public long countPublic(@RequestParam(name = "type") String type) throws Exception {
        return service.countPublic(type);
    }

}
