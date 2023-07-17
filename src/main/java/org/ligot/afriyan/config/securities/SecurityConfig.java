package org.ligot.afriyan.config.securities;

import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //private final JwtAuthenticationFilter jwtAuthFilter;
    //private final AuthenticationProvider authenticationProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("**")//"/swagger-ui/**","/v3/api-docs/**"
                                    .permitAll()
                                    /*.anyRequest()
                                    .authenticated()*/;
                        }
                )
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                //.authenticationProvider(authenticationProvider);
                //.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private final IUtilisateurRepository repository;

    public SecurityConfig(/*JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, */UserDetailsServiceImpl userDetailsService, IUtilisateurRepository repository) {
        this.userDetailsService = userDetailsService;
        //this.jwtAuthFilter = jwtAuthFilter;
        //this.authenticationProvider = authenticationProvider;
        this.repository = repository;
    }

    /*@Bean
    public UserDetailsService userDetailsService(){
        return username -> (UserDetails) repository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not "));
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }*/
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
