package org.ligot.afriyan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.config.KeycloakConfig;
import org.ligot.afriyan.init.PermissionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for listing and discovering available permissions
 */

@RestController
@RequestMapping("/api/permissions")
@Tag(name = "Permissions", description = "APIs for discovering available permissions")
public class PermissionController {
    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);

    @GetMapping
    @Operation(summary = "Get all permissions", description = "Returns a list of all available permissions in the system")
    public ResponseEntity<List<PermissionEnum>> getAllPermissions() {
        log.debug("Fetching all available permissions");
        return ResponseEntity.ok(Arrays.asList(PermissionEnum.values()));
    }

    @GetMapping("/modules")
    @Operation(summary = "Get permissions grouped by module", description = "Returns permissions organized by their module")
    public ResponseEntity<Map<String, List<PermissionEnum>>> getPermissionsByModule() {
        log.debug("Fetching permissions grouped by module");

        Map<String, List<PermissionEnum>> permissionsByModule = Arrays.stream(PermissionEnum.values())
                .collect(Collectors.groupingBy(PermissionEnum::getModule));

        return ResponseEntity.ok(permissionsByModule);
    }

    @GetMapping("/module/{moduleName}")
    @Operation(summary = "Get permissions for a specific module", description = "Returns all permissions for a given module")
    public ResponseEntity<List<PermissionEnum>> getPermissionsByModuleName(@PathVariable String moduleName) {
        log.debug("Fetching permissions for module: {}", moduleName);

        List<PermissionEnum> modulePermissions = Arrays.stream(PermissionEnum.values())
                .filter(p -> p.getModule().equalsIgnoreCase(moduleName))
                .collect(Collectors.toList());

        return ResponseEntity.ok(modulePermissions);
    }

    @GetMapping("/{permission}")
    @Operation(summary = "Get permission details", description = "Returns details about a specific permission")
    public ResponseEntity<Map<String, String>> getPermissionDetails(@PathVariable PermissionEnum permission) {
        log.debug("Fetching details for permission: {}", permission);

        Map<String, String> details = Map.of(
                "name", permission.name(),
                "module", permission.getModule(),
                "action", permission.getAction(),
                "displayName", permission.getDisplayName());

        return ResponseEntity.ok(details);
    }
}
