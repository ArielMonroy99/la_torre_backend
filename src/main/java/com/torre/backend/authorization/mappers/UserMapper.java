package com.torre.backend.authorization.mappers;

import com.torre.backend.authorization.dto.UserDto;
import com.torre.backend.authorization.entities.User;

public class UserMapper {
    public static User toEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        return user;
    }
    public static UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
