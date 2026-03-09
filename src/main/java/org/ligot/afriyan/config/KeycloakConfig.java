package org.ligot.afriyan.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

/**
 * Keycloak configuration for OAuth2 Resource Server
 */

@Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
@ConditionalOnProperty(prefix = "keycloak", name = "enabled", havingValue = "true", matchIfMissing = false)
public class KeycloakConfig {

    private final KeycloakProperties keycloakProperties;
    private static final Logger log = LoggerFactory.getLogger(KeycloakConfig.class);

    public KeycloakConfig(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    /**
     * Keycloak Admin Client for programmatic user management
     */
    @Bean
    public Keycloak keycloakAdminClient() {
        log.info("Initializing Keycloak Admin Client for realm: {}", keycloakProperties.getRealm());

        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getUrl())
                .realm("master") // Admin users live in the master realm
                .clientId("admin-cli") // Built-in admin client for Keycloak REST API
                .grantType(org.keycloak.OAuth2Constants.PASSWORD)
                .username(keycloakProperties.getUsername())
                .password(keycloakProperties.getPassword())
                .build();
    }

    /**
     * JWT Decoder for OAuth2 Resource Server
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = String.format("%s/realms/%s/protocol/openid-connect/certs",
                keycloakProperties.getUrl(),
                keycloakProperties.getRealm());

        log.info("Configuring JWT decoder with JWK Set URI: {}", jwkSetUri);
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}
