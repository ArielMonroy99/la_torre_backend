package com.torre.backend.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.torre.backend.dto.ErrorResponse;
import com.torre.backend.entities.Role;
import com.torre.backend.exceptions.BaseException;
import com.torre.backend.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Enforcer enforcer;
    private final UserService userService;

    @Autowired
    public JwtAuthFilter(JwtService jwtService, Enforcer enforcer, HttpServletRequest httpServletRequest, UserService userService) {
        this.jwtService = jwtService;
        this.enforcer = enforcer;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwtToken = extractJwtFromRequest(request);
            if (StringUtils.hasText(jwtToken) && jwtService.validateToken(jwtToken)) {
                String username = jwtService.extractUsername(jwtToken);
                List<Role> roles = userService.findByUsername(username).getRoles();
                request.setAttribute("roles", roles);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole().name())).toList());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("Security {}",SecurityContextHolder.getContext().getAuthentication());
            } else {
                throw new BaseException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
        } catch (BadCredentialsException | BaseException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            ErrorResponse error = new ErrorResponse();
            error.setMessage(ex.getMessage());
            error.setStatus(HttpStatus.UNAUTHORIZED.value());
            error.setTimestamp(new Timestamp(System.currentTimeMillis()));
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().write(new ObjectMapper().writeValueAsString(error).getBytes());
            return;
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


    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> permittedUrls = List.of("/api/v1/auth/**",
                                            "/api/v1/swagger-ui.html",
                                            "/api/v1/swagger-ui/**",
                                           "/api/v1/v3/api-docs","/api/v1/v3/api-docs/**");
        return permittedUrls.stream().anyMatch(url -> antPathMatcher.match(url, request.getRequestURI()));
    }

}
