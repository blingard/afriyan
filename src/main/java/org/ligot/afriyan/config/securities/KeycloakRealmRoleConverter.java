package org.ligot.afriyan.config.securities;

import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.init.PermissionEnum;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.UtilisateurPermissionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final IUtilisateurRepository utilisateurRepository;
    private final UtilisateurPermissionService permissionService;

    public KeycloakRealmRoleConverter(IUtilisateurRepository utilisateurRepository,
            UtilisateurPermissionService permissionService) {
        this.utilisateurRepository = utilisateurRepository;
        this.permissionService = permissionService;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return extractRoles(jwt).stream()
                .map(authority -> (GrantedAuthority) authority)
                .collect(Collectors.toList());
    }

    private Collection<SimpleGrantedAuthority> extractRoles(Jwt jwt) {

        String userUUID = jwt.getClaim("sub");

        Optional<Utilisateur> utilisateur = utilisateurRepository.findByUuid(userUUID);

        if (utilisateur.isEmpty())
            throw new RuntimeException("Utilisateur non trouvé");

        Set<PermissionEnum> permissions = permissionService.getEffectivePermissions(utilisateur.get().getId());

        Set<String> authorities = permissions.stream().map(PermissionEnum::toString).collect(Collectors.toSet());

        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.toUpperCase()))
                .collect(Collectors.toSet());
    }
}
