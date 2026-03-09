package org.ligot.afriyan.config.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.IUtilisateur;
import org.ligot.afriyan.service.KeycloakService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final IUtilisateurRepository repository;
    private final KeycloakService keycloakService;
    private final JwtDecoder jwtDecoder;
    private final IUtilisateur iUtilisateur;
    private static final String SECRET_KEY = "dR8VhF5zy0R2p8Pjft1U8Zjkexkny35IOUcdMC0rfy0LsVvC0qj94BklHs7py0YBnt8L4bN2IqYlVWksDGMJvg==";

    public JwtService(IUtilisateurRepository repository, KeycloakService keycloakService, JwtDecoder jwtDecoder, IUtilisateur iUtilisateur) {
        this.repository = repository;
        this.keycloakService = keycloakService;
        this.jwtDecoder = jwtDecoder;
        this.iUtilisateur = iUtilisateur;
    }

    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 36000 * 24)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
        return token;
    }

    public String generateTokenRefresh(Long id) {
        return Jwts
                .builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (10000 * 60 * 24)))
                .compact();
    }

    public String generateRefreshToken(Long id) {
        return generateTokenRefresh(id);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extraExpiration(token).before(new Date());
    }

    private Date extraExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
