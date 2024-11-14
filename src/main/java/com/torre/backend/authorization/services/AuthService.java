package com.torre.backend.authorization.services;

import com.torre.backend.authorization.configurations.JwtService;
import com.torre.backend.authorization.dto.AuthenticationRequestDto;
import com.torre.backend.authorization.dto.AuthenticationResponseDto;
import com.torre.backend.authorization.entities.User;
import com.torre.backend.common.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        try {
            log.info("Auth request: {}", authenticationRequestDto);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());
            User user = userService.findByUsername(userDetails.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateToken(userDetails);
            AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
            authenticationResponseDto.setUser(user);
            authenticationResponseDto.setAccessToken(token);
            return authenticationResponseDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(HttpStatus.UNAUTHORIZED,"username or password incorrect");
        }
    }
}
