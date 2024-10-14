package com.torre.backend.configurations;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
   @Value("${jwt.expiration}")
   private int EXPIRATION_TIME;
   public static final Logger logger = LoggerFactory.getLogger(JwtService.class);
   public String generateToken(UserDetails userDetails){
       Map<String, Object> claims = new HashMap<>();
       Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
       List<String> authorities = new ArrayList<>();
       for (GrantedAuthority role : roles) {
           authorities.add("ROLE_" + role.getAuthority());
       }
       claims.put("roles", authorities);
       return doGenerateToken(claims, userDetails.getUsername());
   }

   private String doGenerateToken(Map<String, Object> claims, String subject){
       byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);//this has to be base-64 encoded, it reads 'secret' if we de-encoded it
       SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);
       return Jwts.builder().subject(subject)
               .claims(claims)
               .issuedAt(new Date(System.currentTimeMillis()))
               .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
               .signWith(key)
               .compact();
   }
   public String extractUsername(String token){
       return extractClaim(token, Claims::getSubject);
   }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);//this has to be base-64 encoded, it reads 'secret' if we de-encoded it
        SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);
        JwtParser parser = Jwts.parser().verifyWith(key).build();
        return parser.parseSignedClaims(token).getPayload();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Boolean validateToken(String token) {
        try {
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);//this has to be base-64 encoded, it reads 'secret' if we de-encoded it
            SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);
            JwtParser parser = Jwts.parser().verifyWith(key).build();
            parser.parseSignedClaims(token).getPayload();
            return true;
        } catch ( MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }
    }
    public List<SimpleGrantedAuthority> getRoles(String token){
        Claims claims = extractAllClaims(token);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        List roleClaims = claims.get("roles", List.class);
        for(Object roleClaim: roleClaims){
            roles.add(new SimpleGrantedAuthority((String) roleClaim)); // Solo agregar el rol
        }
        return roles;
    }

}
