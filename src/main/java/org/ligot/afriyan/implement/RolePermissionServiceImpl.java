package org.ligot.afriyan.implement;

import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.init.PermissionEnum;
import org.ligot.afriyan.repository.IGroupesRepository;
import org.ligot.afriyan.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of RolePermissionService
 */
@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {
    private static final Logger log = LoggerFactory.getLogger(RolePermissionServiceImpl.class);


    private final IGroupesRepository groupesRepository;

    public RolePermissionServiceImpl(IGroupesRepository groupesRepository) {
        this.groupesRepository = groupesRepository;
    }


    @Override
    public void addPermissionToRole(Long roleId, Set<PermissionEnum> permission) {
        log.info("Adding permission {} to role {}", permission, roleId);

        Groupes role = groupesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        role.getPermissions().addAll(permission);
        groupesRepository.save(role);

        log.info("Permission {} added successfully to role {}", permission, roleId);
    }

    @Override
    public void removePermissionFromRole(Long roleId, PermissionEnum permission) {
        log.info("Removing permission {} from role {}", permission, roleId);

        Groupes role = groupesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        role.getPermissions().remove(permission);
        groupesRepository.save(role);

        log.info("Permission {} removed successfully from role {}", permission, roleId);
    }

    @Override
    public void addPermissionsToRole(Long roleId, Set<PermissionEnum> permissions) {
        log.info("Adding {} permissions to role {}", permissions.size(), roleId);

        Groupes role = groupesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        role.getPermissions().addAll(permissions);
        groupesRepository.save(role);

        log.info("{} permissions added successfully to role {}", permissions.size(), roleId);
    }

    @Override
    public Set<PermissionEnum> getRolePermissions(Long roleId) {
        log.debug("Fetching permissions for role {}", roleId);

        Groupes role = groupesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        return new HashSet<>(role.getPermissions());
    }

    @Override
    public void syncRolePermissions(Long roleId, Set<PermissionEnum> permissions) {
        log.info("Synchronizing permissions for role {}: {} permissions", roleId, permissions.size());

        Groupes role = groupesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        role.setPermissions(new HashSet<>(permissions));
        groupesRepository.save(role);

        log.info("Permissions synchronized successfully for role {}", roleId);
    }

    @Override
    public boolean roleHasPermission(Long roleId, PermissionEnum permission) {
        log.debug("Checking if role {} has permission {}", roleId, permission);

        Groupes role = groupesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        boolean hasPermission = role.getPermissions().contains(permission);

        log.debug("Role {} {} permission {}", roleId, hasPermission ? "has" : "does NOT have", permission);
        return hasPermission;
    }

    @Override
    public void clearRolePermissions(Long roleId) {
        log.info("Clearing all permissions for role {}", roleId);

        Groupes role = groupesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        role.getPermissions().clear();
        groupesRepository.save(role);

        log.info("All permissions cleared for role {}", roleId);
    }
}
