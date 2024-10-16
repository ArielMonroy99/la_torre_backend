package com.torre.backend.authorization.controllers;

import com.torre.backend.authorization.dto.AuthenticationRequestDto;
import com.torre.backend.authorization.services.AuthService;
import com.torre.backend.common.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController()
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity<ResponseDto<?>> auth(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return ResponseEntity.ok(new ResponseDto<>(true, "Authenticated successfully",authService.authenticate(authenticationRequestDto)));
    }
}
