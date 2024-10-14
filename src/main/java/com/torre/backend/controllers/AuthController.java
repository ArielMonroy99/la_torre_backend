package com.torre.backend.controllers;

import com.torre.backend.configurations.CustomUserDetailsService;
import com.torre.backend.configurations.JwtService;
import com.torre.backend.dto.AuthenticationRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller()
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody AuthenticationRequestDto authenticationRequestDto,
                                       HttpServletRequest request) {
       try {
           logger.info("Auth request: {}", authenticationRequestDto);
           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword())
           );
           UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());
           SecurityContextHolder.getContext().setAuthentication(authentication);
           String token = jwtService.generateToken(userDetails);
           return ResponseEntity.ok(token);
       } catch (Exception e) {
           logger.error(e.getMessage());
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
