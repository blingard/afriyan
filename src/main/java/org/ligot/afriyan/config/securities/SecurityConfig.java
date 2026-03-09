package org.ligot.afriyan.config.securities;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.UserDetailsServiceImpl;
import org.ligot.afriyan.service.UtilisateurPermissionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    // private final JwtAuthenticationFilter jwtAuthFilter;
    private final PasswordEncoder passwordEncoder;
    private final IUtilisateurRepository utilisateurRepository;
    private final UtilisateurPermissionService permissionService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, /* JwtAuthenticationFilter jwtAuthFilter, */
            @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder, IUtilisateurRepository utilisateurRepository,
            UtilisateurPermissionService permissionService) {
        this.userDetailsService = userDetailsService;
        // this.jwtAuthFilter = jwtAuthFilter;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurRepository = utilisateurRepository;
        this.permissionService = permissionService;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth
                                    .requestMatchers(
                                            "/actuator/**",
                                            "/swagger-ui/**",
                                            "/swagger-ui-custom.html",
                                            "/v3/api-docs/**",
                                            "/api/auth/**", // Auth endpoints
                                            "/api/public/**", // Public API
                                            "/public/**" // Common public paths
                            )
                                    .permitAll()
                                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated();
                        })

                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // .authenticationProvider(authenticationProvider())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())));
        // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(
                new KeycloakRealmRoleConverter(utilisateurRepository, permissionService));
        return converter;
    }

    /*
     * @Bean
     * public AuthenticationProvider authenticationProvider() {
     * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
     * provider.setUserDetailsService(userDetailsService);
     * provider.setPasswordEncoder(passwordEncoder);
     * return provider;
     * }
     */

}
