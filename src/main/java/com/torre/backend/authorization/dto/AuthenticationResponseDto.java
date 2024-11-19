package com.torre.backend.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {

  private String accessToken;
  private String refreshToken;
  private UserDto user;
}
