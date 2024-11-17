package com.torre.backend.authorization.repositories;

import com.torre.backend.authorization.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  void deleteByToken(String token);

  boolean existsByToken(String token);
}
