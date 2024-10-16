package com.torre.backend.authorization.dto;

import com.torre.backend.authorization.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private String accessToken;
    private User user;
}
