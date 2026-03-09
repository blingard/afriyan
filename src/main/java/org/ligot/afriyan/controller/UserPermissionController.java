package org.ligot.afriyan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ligot.afriyan.Dto.PermissionRequest;
import org.ligot.afriyan.Dto.RoleAssignmentRequest;
import org.ligot.afriyan.Dto.SyncPermissionsRequest;
import org.ligot.afriyan.init.PermissionEnum;
import org.ligot.afriyan.service.UtilisateurPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Set;

/**
 * Controller for managing user permissions
 */
@RestController
@RequestMapping("/api/users/{userId}/permissions")
@Tag(name = "User Permissions", description = "APIs for managing user permissions")
public class UserPermissionController {

    private static final Logger log = LoggerFactory.getLogger(UserPermissionController.class);


    private final UtilisateurPermissionService permissionService;

    public UserPermissionController(UtilisateurPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @Operation(summary = "Get user's direct permissions", description = "Returns only permissions directly assigned to the user")
    @PreAuthorize("hasAuthority('PERMISSION_USER_READ') or #userId == authentication.principal.id")
    public ResponseEntity<Set<PermissionEnum>> getUserDirectPermissions(@PathVariable Long userId) {
        log.info("Fetching direct permissions for user {}", userId);
        Set<PermissionEnum> permissions = permissionService.getUserDirectPermissions(userId);
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/effective")
    @Operation(summary = "Get user's effective permissions", description = "Returns all permissions including those from roles")
    @PreAuthorize("hasAuthority('PERMISSION_USER_READ') or #userId == authentication.principal.id")
    public ResponseEntity<Set<PermissionEnum>> getEffectivePermissions(@PathVariable Long userId) {
        log.info("Fetching effective permissions for user {}", userId);
        Set<PermissionEnum> permissions = permissionService.getEffectivePermissions(userId);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping
    @Operation(summary = "Add a permission to user", description = "Adds a single permission directly to the user")
    @PreAuthorize("hasAuthority('PERMISSION_USER_MANAGE_PERMISSIONS')")
    public ResponseEntity<Void> addPermissionToUser(
            @PathVariable Long userId,
            @Valid @RequestBody PermissionRequest request) {
        log.info("Adding permission {} to user {}", request.getPermission(), userId);
        permissionService.addPermissionToUser(userId, request.getPermission());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{permission}")
    @Operation(summary = "Remove a permission from user", description = "Removes a direct permission from the user")
    @PreAuthorize("hasAuthority('PERMISSION_USER_MANAGE_PERMISSIONS')")
    public ResponseEntity<Void> removePermissionFromUser(
            @PathVariable Long userId,
            @PathVariable PermissionEnum permission) {
        log.info("Removing permission {} from user {}", permission, userId);
        permissionService.removePermissionFromUser(userId, permission);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sync")
    @Operation(summary = "Synchronize user permissions", description = "Replaces all direct user permissions with the provided set")
    @PreAuthorize("hasAuthority('PERMISSION_USER_MANAGE_PERMISSIONS')")
    public ResponseEntity<Void> syncUserPermissions(
            @PathVariable Long userId,
            @Valid @RequestBody SyncPermissionsRequest request) {
        log.info("Synchronizing permissions for user {}", userId);
        permissionService.syncUserPermissions(userId, request.getPermissions());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check/{permission}")
    @Operation(summary = "Check if user has permission", description = "Checks if user has a specific permission (direct or through roles)")
    @PreAuthorize("hasAuthority('PERMISSION_USER_READ') or #userId == authentication.principal.id")
    public ResponseEntity<Boolean> checkPermission(
            @PathVariable Long userId,
            @PathVariable PermissionEnum permission) {
        log.info("Checking permission {} for user {}", permission, userId);
        boolean hasPermission = permissionService.userHasPermission(userId, permission);
        return ResponseEntity.ok(hasPermission);
    }

    // Role management for users
    @PostMapping("/../roles")
    @Operation(summary = "Assign role to user", description = "Adds a role to the user")
    @PreAuthorize("hasAuthority('PERMISSION_USER_MANAGE_ROLES')")
    public ResponseEntity<Void> addRoleToUser(
            @PathVariable Long userId,
            @Valid @RequestBody RoleAssignmentRequest request) {
        log.info("Adding role {} to user {}", request.getRoleId(), userId);
        permissionService.addRoleToUser(userId, request.getRoleId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/../roles/{roleId}")
    @Operation(summary = "Remove role from user", description = "Removes a role from the user")
    @PreAuthorize("hasAuthority('PERMISSION_USER_MANAGE_ROLES')")
    public ResponseEntity<Void> removeRoleFromUser(
            @PathVariable Long userId,
            @PathVariable Long roleId) {
        log.info("Removing role {} from user {}", roleId, userId);
        permissionService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/../roles")
    @Operation(summary = "Get user roles", description = "Returns all role IDs assigned to the user")
    @PreAuthorize("hasAuthority('PERMISSION_USER_READ') or #userId == authentication.principal.id")
    public ResponseEntity<Set<Long>> getUserRoles(@PathVariable Long userId) {
        log.info("Fetching roles for user {}", userId);
        Set<Long> roleIds = permissionService.getUserRoles(userId);
        return ResponseEntity.ok(roleIds);
    }
}
