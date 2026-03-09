package org.ligot.afriyan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.PermissionRequest;
import org.ligot.afriyan.Dto.SyncPermissionsRequest;
import org.ligot.afriyan.init.PermissionEnum;
import org.ligot.afriyan.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Set;

/**
 * Controller for managing role permissions
 */

@RestController
@RequestMapping("/api/groupe/{groupeId}/permissions")
@Tag(name = "Role Permissions", description = "APIs for managing role permissions")
public class RolePermissionController {
    private static final Logger log = LoggerFactory.getLogger(RolePermissionController.class);


    private final RolePermissionService permissionService;

    public RolePermissionController(RolePermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @Operation(summary = "Get role permissions", description = "Returns all permissions assigned to the role")
    @RolesAllowed("PERMISSION_ROLE_READ")
    public ResponseEntity<Set<PermissionEnum>> getRolePermissions(@PathVariable Long groupeId) {
        log.info("Fetching permissions for groupeId {}", groupeId);
        Set<PermissionEnum> permissions = permissionService.getRolePermissions(groupeId);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping
    @Operation(summary = "Add a permission to role", description = "Adds a single permission to the role")
    @RolesAllowed("PERMISSION_ROLE_MANAGE_PERMISSIONS")
    public ResponseEntity<Void> addPermissionToRole(
            @PathVariable Long groupeId,
            @Valid @RequestBody PermissionRequest request) {
        log.info("Adding permission {} to groupeId {}", request.getPermission(), groupeId);
        permissionService.addPermissionToRole(groupeId, request.getPermission());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{permission}")
    @Operation(summary = "Remove a permission from role", description = "Removes a permission from the role")
    @RolesAllowed("PERMISSION_ROLE_MANAGE_PERMISSIONS")
    public ResponseEntity<Void> removePermissionFromRole(
            @PathVariable Long groupeId,
            @PathVariable PermissionEnum permission) {
        log.info("Removing permission {} from groupeId {}", permission, groupeId);
        permissionService.removePermissionFromRole(groupeId, permission);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sync")
    @Operation(summary = "Synchronize role permissions", description = "Replaces all role permissions with the provided set")
    @RolesAllowed("PERMISSION_ROLE_MANAGE_PERMISSIONS")
    public ResponseEntity<Void> syncRolePermissions(
            @PathVariable Long roleId,
            @Valid @RequestBody SyncPermissionsRequest request) {
        log.info("Synchronizing permissions for role {}", roleId);
        permissionService.syncRolePermissions(roleId, request.getPermissions());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "Clear all role permissions", description = "Removes all permissions from the role")
    @RolesAllowed("PERMISSION_ROLE_MANAGE_PERMISSIONS")
    public ResponseEntity<Void> clearRolePermissions(@PathVariable Long roleId) {
        log.info("Clearing all permissions for role {}", roleId);
        permissionService.clearRolePermissions(roleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check/{permission}")
    @Operation(summary = "Check if role has permission", description = "Checks if role has a specific permission")
    @RolesAllowed("PERMISSION_ROLE_MANAGE_PERMISSIONS")
    public ResponseEntity<Boolean> checkPermission(
            @PathVariable Long roleId,
            @PathVariable PermissionEnum permission) {
        log.info("Checking permission {} for role {}", permission, roleId);
        boolean hasPermission = permissionService.roleHasPermission(roleId, permission);
        return ResponseEntity.ok(hasPermission);
    }
}
