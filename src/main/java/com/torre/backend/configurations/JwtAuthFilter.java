package com.torre.backend.configurations;

import com.torre.backend.entities.Role;
import com.torre.backend.exceptions.BaseException;
import com.torre.backend.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Enforcer enforcer;
    private final UserService userService;
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
            response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
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
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/v1/auth");

    }
}
