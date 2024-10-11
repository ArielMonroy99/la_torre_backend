package com.torre.backend.configurations;

import ch.qos.logback.core.util.StringUtil;
import com.torre.backend.services.UserInfoService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    public static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final JwtService jwtService;
    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try{
           String jwtToken = extractJwtFromRequest(request);
           if(StringUtils.hasText(jwtToken) && jwtService.validateToken(jwtToken)){
              UserDetails userDetails = new User(jwtService.extractUsername(jwtToken),"", jwtService.getRoles(jwtToken));
              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("{}", authentication);
           } else {
                logger.error("Cannot set the Security Context");
           }
       } catch (BadCredentialsException ex) {
           throw new BadCredentialsException("Invalid username or password");
       }
        filterChain.doFilter(request, response);
    }
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
