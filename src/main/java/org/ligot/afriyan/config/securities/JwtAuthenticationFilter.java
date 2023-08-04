package org.ligot.afriyan.config.securities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.ligot.afriyan.Dto.UserDetailsImpl;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final IUtilisateurRepository repository;

    public JwtAuthenticationFilter(JwtService jwtService, IUtilisateurRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        System.err.println(request.getContextPath());
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(07);
        userName = jwtService.extractUserName(jwt);
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            Utilisateur utilisateur = repository.findByEmail(userName).orElse(null);
            if(utilisateur == null)
                throw new ServletException("user not found");
            UserDetails userDetails = UserDetailsImpl.build(utilisateur, utilisateur.getGroupe().getRoles());
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
