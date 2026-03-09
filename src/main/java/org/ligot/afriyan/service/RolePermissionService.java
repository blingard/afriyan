package org.ligot.afriyan.service;

import org.ligot.afriyan.init.PermissionEnum;

import java.util.Set;

/**
 * Service for managing role permissions
 */
public interface RolePermissionService {

    /**
     * Add a permission to a role
     *
     * @param roleId     role ID
     * @param permission permission to add
     */
    void addPermissionToRole(Long roleId, Set<PermissionEnum> permission);

    /**
     * Remove a permission from a role
     *
     * @param roleId     role ID
     * @param permission permission to remove
     */
    void removePermissionFromRole(Long roleId, PermissionEnum permission);

    /**
     * Add multiple permissions to a role
     *
     * @param roleId      role ID
     * @param permissions permissions to add
     */
    void addPermissionsToRole(Long roleId, Set<PermissionEnum> permissions);

    /**
     * Get all permissions for a role
     *
     * @param roleId role ID
     * @return set of permissions
     */
    Set<PermissionEnum> getRolePermissions(Long roleId);

    /**
     * Synchronize/replace all role permissions
     *
     * @param roleId      role ID
     * @param permissions new set of permissions
     */
    void syncRolePermissions(Long roleId, Set<PermissionEnum> permissions);

    /**
     * Check if a role has a specific permission
     *
     * @param roleId     role ID
     * @param permission permission to check
     * @return true if role has the permission
     */
    boolean roleHasPermission(Long roleId, PermissionEnum permission);

    /**
     * Remove all permissions from a role
     *
     * @param roleId role ID
     */
    void clearRolePermissions(Long roleId);
}
