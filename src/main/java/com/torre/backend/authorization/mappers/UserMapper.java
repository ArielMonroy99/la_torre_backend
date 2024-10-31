package com.torre.backend.authorization.mappers;

import com.torre.backend.authorization.dto.CreateUserDto;
import com.torre.backend.authorization.dto.UpdatePasswordDto;
import com.torre.backend.authorization.dto.UserDto;
import com.torre.backend.authorization.entities.Role;
import com.torre.backend.authorization.entities.User;

public class UserMapper {
    public static User toEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setCellphone(userDto.getCellphone());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        return user;
    }
    public static UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setCellphone(user.getCellphone());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public static User createEntity(CreateUserDto userDto, Role role) {
        User user = new User();
        return getUser(user, userDto, role);
    }

    private static User getUser(User user, CreateUserDto userDto, Role role) {
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setCellphone(userDto.getCellphone());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(role);
        return user;
    }

    public static User updateEntity(User user, CreateUserDto userDto, Role role) {
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setCellphone(userDto.getCellphone());
        user.setUsername(userDto.getUsername());
        user.setRole(role);
        return user;
    }

    public static User updatePassword(User user, UpdatePasswordDto passwordDto) {
        user.setPassword(passwordDto.getNewPassword());
        return user;
    }
}
