package org.ligot.afriyan.config.securities;

        import org.ligot.afriyan.service.UserDetailsServiceImpl;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.http.HttpMethod;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.authentication.AuthenticationProvider;
        import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
        import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
        import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.security.web.SecurityFilterChain;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
        import org.springframework.web.cors.CorsConfiguration;
        import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
        import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final PasswordEncoder passwordEncoder;
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthFilter, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

      /*AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());*/
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> {
                            auth
                                    .requestMatchers("/swagger-ui/**","/v3/api-docs/**")
                                    .permitAll()
                                    .requestMatchers("/api/auth/**")
                                    .permitAll()
                                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                                    .permitAll()
                                    .anyRequest()
                                    .permitAll();
                            //.authenticated();
                        }
                )
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //http.userDetailsService(userDetailsService);

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
