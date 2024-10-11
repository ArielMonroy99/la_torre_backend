package com.torre.backend.configurations;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {
   @Value("${jwt.secret}")
   private String SECRET_KEY;
   @Value("${jwt.expiration}")
   private int EXPIRATION_TIME;
   public static final Logger logger = LoggerFactory.getLogger(JwtService.class);
   public String generateToken(UserDetails userDetails){
       Map<String, Object> claims = new HashMap<>();
       logger.info("{}", userDetails.getAuthorities());
       Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
       for(GrantedAuthority role: roles){
           claims.put("roles", Collections.singletonList(new SimpleGrantedAuthority(role.getAuthority())));
       }
       return doGenerateToken(claims, userDetails.getUsername());
   }

   private String doGenerateToken(Map<String, Object> claims, String subject) {
       return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
               .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
   }
   public String extractUsername(String token){
       return extractClaim(token, Claims::getSubject);
   }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }
    }
    public List<SimpleGrantedAuthority> getRoles(String token){
        Claims claims = extractAllClaims(token);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        List<Map<String, String>> roleClaims = claims.get("roles", List.class);
        for(Map<String, String> roleClaim: roleClaims){
            roles.add(new SimpleGrantedAuthority(roleClaim.get("authority")));
        }
        return roles;
    }
}
