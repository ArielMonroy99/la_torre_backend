package com.torre.backend.authorization.services;

import com.torre.backend.authorization.configurations.JwtService;
import com.torre.backend.authorization.dto.AuthenticationRequestDto;
import com.torre.backend.authorization.dto.AuthenticationResponseDto;
import com.torre.backend.authorization.entities.RefreshToken;
import com.torre.backend.authorization.entities.User;
import com.torre.backend.authorization.mappers.UserMapper;
import com.torre.backend.common.exceptions.BaseException;
import jakarta.transaction.Transactional;
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
  private final RefreshTokenService refreshTokenService;

  public AuthService(AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService,
      JwtService jwtService, UserService userService,
      RefreshTokenService refreshTokenService) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
    this.userService = userService;

    this.refreshTokenService = refreshTokenService;
  }

  public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
    try {
      log.info("Auth request: {}", authenticationRequestDto);
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(),
              authenticationRequestDto.getPassword()));
      UserDetails userDetails = userDetailsService.loadUserByUsername(
          authenticationRequestDto.getUsername());
      User user = userService.findByUsername(userDetails.getUsername());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token = jwtService.generateToken(userDetails);
      RefreshToken refreshToken = refreshTokenService.create(userDetails);
      AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
      authenticationResponseDto.setUser(UserMapper.toDto(user));
      authenticationResponseDto.setAccessToken(token);
      authenticationResponseDto.setRefreshToken(refreshToken.getToken());

      return authenticationResponseDto;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new BaseException(HttpStatus.UNAUTHORIZED, "username or password incorrect");
    }
  }

  @Transactional()
  public AuthenticationResponseDto refreshToken(String refreshToken) {
    if (refreshToken == null) {
      throw new BaseException(HttpStatus.UNAUTHORIZED, "refresh token is null");
    }
    if (jwtService.validateToken(refreshToken) && refreshTokenService.checkRefreshToken(
        refreshToken)) {
      refreshTokenService.deleteRefreshToken(refreshToken);
      String username = jwtService.extractIssuer(refreshToken);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      jwtService.generateToken(userDetails);
      RefreshToken newRefreshToken = refreshTokenService.create(userDetails);

      AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
      authenticationResponseDto.setAccessToken(refreshToken);
      authenticationResponseDto.setRefreshToken(newRefreshToken.getToken());
      return authenticationResponseDto;
    }
    throw new BaseException(HttpStatus.UNAUTHORIZED, "refresh token expired");
  }
}
