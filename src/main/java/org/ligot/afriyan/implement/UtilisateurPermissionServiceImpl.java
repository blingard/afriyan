package org.ligot.afriyan.implement;

import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.init.PermissionEnum;
import org.ligot.afriyan.repository.IRolesRepository;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.UtilisateurPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of UtilisateurPermissionService
 * NOTE: @Transactional is applied per-method (not at class level) to avoid
 * Spring AOP proxy circular calls that cause StackOverflowError during JWT
 * auth.
 */
@Service
public class UtilisateurPermissionServiceImpl implements UtilisateurPermissionService {

    private final IUtilisateurRepository utilisateurRepository;
    private final IRolesRepository rolesRepository;

    public UtilisateurPermissionServiceImpl(IUtilisateurRepository utilisateurRepository,
            IRolesRepository rolesRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    @Transactional
    public void addPermissionToUser(Long userId, Set<PermissionEnum> permission) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        user.getPermissionAdd().addAll(permission);
        user.getPermissionRemove().remove(permission);
        utilisateurRepository.save(user);
    }

    @Override
    @Transactional
    public void removePermissionFromUser(Long userId, PermissionEnum permission) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        user.getPermissionRemove().add(permission);
        user.getPermissionAdd().remove(permission);
        utilisateurRepository.save(user);
    }

    @Override
    @Transactional
    public void addPermissionsToUser(Long userId, Set<PermissionEnum> permissions) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        user.getPermissionAdd().addAll(permissions);
        user.getPermissionRemove().removeAll(permissions);
        utilisateurRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<PermissionEnum> getUserDirectPermissions(Long userId) {
        // Appel direct de la logique (méthode privée) pour éviter le proxy AOP
        return computeEffectivePermissions(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<PermissionEnum> getEffectivePermissions(Long userId) {
        return computeEffectivePermissions(userId);
    }

    /**
     * Logique interne non-proxifiable pour calculer les permissions effectives.
     * Évite le StackOverflow causé par les appels inter-méthodes via proxy AOP.
     */
    private Set<PermissionEnum> computeEffectivePermissions(Long userId) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Groupes groupes = user.getGroupe();
        Set<PermissionEnum> permissions = new HashSet<>(groupes != null ? groupes.getPermissions() : new HashSet<>());
        permissions.addAll(user.getPermissionAdd());
        permissions.removeAll(user.getPermissionRemove());
        return permissions;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userHasPermission(Long userId, PermissionEnum permission) {
        return computeEffectivePermissions(userId).contains(permission);
    }

    @Override
    @Transactional
    public void syncUserPermissions(Long userId, Set<PermissionEnum> permissions) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        user.setPermissionAdd(new HashSet<>(permissions));
        user.getPermissionRemove().removeAll(new HashSet<>(permissions));
        utilisateurRepository.save(user);
    }

    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        // //log.info("Adding role {} to user {}", roleId, userId);

        // Utilisateur user = utilisateurRepository.findById(userId)
        // .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Roles role = rolesRepository.findById(roleId)
        // .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        // user.getRoles().add(role);
        // utilisateurRepository.save(user);

        // //log.info("Role {} added successfully to user {}", roleId, userId);
    }

    @Override
    public void removeRoleFromUser(Long userId, Long roleId) {
        // //log.info("Removing role {} from user {}", roleId, userId);

        // Utilisateur user = utilisateurRepository.findById(userId)
        // .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Roles role = rolesRepository.findById(roleId)
        // .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        // user.getRoles().remove(role);
        // utilisateurRepository.save(user);

        // //log.info("Role {} removed successfully from user {}", roleId, userId);
    }

    @Override
    public Set<Long> getUserRoles(Long userId) {
        return new HashSet<>();
        // //log.debug("Fetching roles for user {}", userId);

        // Utilisateur user = utilisateurRepository.findById(userId)
        // .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // return user.getRoles().stream()
        // .map(Roles::getId)
        // .collect(Collectors.toSet());
    }
}
