package com.torre.backend.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String lastname;
    private Integer cellphone;
    private RoleDto role;
}
