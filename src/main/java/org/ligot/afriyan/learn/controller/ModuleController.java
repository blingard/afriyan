package org.ligot.afriyan.learn.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public/api/learn/modules")
@CrossOrigin("*")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping
    @RolesAllowed(value = {"CREATE_MODULE"})
    public ResponseEntity<ModuleDTO> createModule(@RequestBody ModuleCreateDTO dto) {
        ModuleDTO created = moduleService.createModule(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RolesAllowed(value = {"UPDATE_MODULE"})
    public ResponseEntity<ModuleDTO> updateModule(
            @PathVariable String id,
            @RequestBody ModuleCreateDTO dto) {
        ModuleDTO updated = moduleService.updateModule(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @RolesAllowed(value = {"GET_MODULE"})
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable String id) {
        ModuleDTO module = moduleService.getModuleById(id);
        return ResponseEntity.ok(module);
    }

    @GetMapping("/formation/{formationId}")
    @RolesAllowed(value = {"GET_MODULE"})
    public ResponseEntity<List<ModuleDTO>> getModulesByFormationId(@PathVariable String formationId) {
        List<ModuleDTO> modules = moduleService.getModulesByFormationId(formationId);
        return ResponseEntity.ok(modules);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"DELETE_MODULE"})
    public ResponseEntity<Void> deleteModule(@PathVariable String id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/formation/{formationId}/reorder")
    @RolesAllowed(value = {"UPDATE_MODULE"})
    public ResponseEntity<Void> reorderModules(
            @PathVariable String formationId,
            @RequestBody List<String> moduleIds) {
        moduleService.reorderModules(formationId, moduleIds);
        return ResponseEntity.ok().build();
    }
}
