package com.torre.backend.authorization.mappers;

import com.torre.backend.authorization.dto.RoleDto;
import com.torre.backend.authorization.entities.Role;
import com.torre.backend.authorization.enums.RolEnum;


public class RoleMapper {
    public static Role toEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setRole(RolEnum.valueOf(roleDto.getRole()));
        return role;
    }

    public static RoleDto toDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setRole(role.getRole().toString());
        return roleDto;
    }
}
