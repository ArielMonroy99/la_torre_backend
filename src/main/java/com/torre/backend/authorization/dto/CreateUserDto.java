package com.torre.backend.authorization.dto;

import com.torre.backend.authorization.entities.Auditable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto extends Auditable {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String lastname;
    @NotNull
    @NotEmpty
    private Integer cellphone;
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private Long roleId;
}