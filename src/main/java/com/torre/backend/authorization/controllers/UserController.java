package com.torre.backend.authorization.controllers;

import com.torre.backend.authorization.annotations.CasbinFilter;
import com.torre.backend.authorization.dto.CreateUserDto;
import com.torre.backend.authorization.dto.UpdatePasswordDto;
import com.torre.backend.authorization.enums.StatusEnum;
import com.torre.backend.authorization.mappers.UserMapper;
import com.torre.backend.authorization.services.UserService;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.dtos.ResponseDto;
import com.torre.backend.common.exceptions.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/user")
@Tag(name = "Users")
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Creates a new User")
    @CasbinFilter
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto userDto, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        userService.createUser(userDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfull", null));
    }
    @GetMapping
    @Operation(summary = "Gets all the users and filter for role")
    @CasbinFilter
    public ResponseEntity<?> getUsers(@ModelAttribute QueryParamsDto queryParamsDto, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfull",userService.findAll(queryParamsDto)));
    }

    @GetMapping("/profile")
    @Operation(summary = "Gets the user")
    @CasbinFilter
    public ResponseEntity<?> getUser(@ModelAttribute QueryParamsDto queryParamsDto, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfull", UserMapper.toDto(userService.findByUsername(username))));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing user")
    @CasbinFilter
    public ResponseEntity<?> updateUser(@PathVariable ("id") Long id, @RequestBody CreateUserDto createUserDto, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        userService.updateUser(id,createUserDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfull", null));
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate User")
    @CasbinFilter
    public ResponseEntity<?> activateUser(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        this.userService.updateUserStatus(id, StatusEnum.ACTIVE,username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",null));
    }

    @PutMapping("/{id}/inactivate")
    @Operation(summary = "Inactivates User")
    @CasbinFilter
    public ResponseEntity<?> inactivateUser(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        this.userService.updateUserStatus(id, StatusEnum.INACTIVE,username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",null));
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "Updates password of an existing user")
    @CasbinFilter
    public ResponseEntity<?> updatePassword(@PathVariable ("id") Long id, @RequestBody UpdatePasswordDto updatePasswordDto, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        userService.updatePassword(id,updatePasswordDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfully", null));
    }
}
