package org.ligot.afriyan.service;

import org.ligot.afriyan.init.PermissionEnum;

import java.util.Set;

/**
 * Service for managing user permissions (direct permissions assigned to users)
 */
public interface UtilisateurPermissionService {

    /**
     * Add a permission to a user
     *
     * @param userId     user ID
     * @param permission permission to add
     */
    void addPermissionToUser(Long userId, Set<PermissionEnum> permission);

    /**
     * Remove a permission from a user
     *
     * @param userId     user ID
     * @param permission permission to remove
     */
    void removePermissionFromUser(Long userId, PermissionEnum permission);

    /**
     * Add multiple permissions to a user
     *
     * @param userId      user ID
     * @param permissions permissions to add
     */
    void addPermissionsToUser(Long userId, Set<PermissionEnum> permissions);

    /**
     * Get direct permissions of a user (not including role permissions)
     *
     * @param userId user ID
     * @return set of direct permissions
     */
    Set<PermissionEnum> getUserDirectPermissions(Long userId);

    /**
     * Get all effective permissions for a user (direct + from roles)
     *
     * @param userId user ID
     * @return set of all effective permissions
     */
    Set<PermissionEnum> getEffectivePermissions(Long userId);

    /**
     * Check if a user has a specific permission (either direct or through roles)
     *
     * @param userId     user ID
     * @param permission permission to check
     * @return true if user has the permission
     */
    boolean userHasPermission(Long userId, PermissionEnum permission);

    /**
     * Synchronize/replace all user direct permissions
     *
     * @param userId      user ID
     * @param permissions new set of permissions
     */
    void syncUserPermissions(Long userId, Set<PermissionEnum> permissions);

    /**
     * Add a role to a user
     *
     * @param userId user ID
     * @param roleId role ID
     */
    void addRoleToUser(Long userId, Long roleId);

    /**
     * Remove a role from a user
     *
     * @param userId user ID
     * @param roleId role ID
     */
    void removeRoleFromUser(Long userId, Long roleId);

    /**
     * Get all roles assigned to a user
     *
     * @param userId user ID
     * @return set of role IDs
     */
    Set<Long> getUserRoles(Long userId);
}
