package com.torre.backend.authorization.services;

import com.torre.backend.authorization.configurations.JwtService;
import com.torre.backend.authorization.entities.RefreshToken;
import com.torre.backend.authorization.repositories.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtService jwtService;

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.jwtService = jwtService;
  }

  @Transactional()
  public RefreshToken create(UserDetails userDetails) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken = refreshTokenRepository.save(refreshToken);
    String token = jwtService.generateRefreshToken(userDetails, refreshToken.getId());
    refreshToken.setToken(token);
    refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  @Transactional()
  public void deleteRefreshToken(String refreshToken) {
    refreshTokenRepository.deleteByToken(refreshToken);
  }

  @Transactional()
  public boolean checkRefreshToken(String refreshToken) {
    return refreshTokenRepository.existsByToken(refreshToken);
  }
}
