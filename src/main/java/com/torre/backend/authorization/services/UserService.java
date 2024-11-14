package com.torre.backend.authorization.services;

import com.torre.backend.authorization.dto.CreateUserDto;
import com.torre.backend.authorization.dto.UpdatePasswordDto;
import com.torre.backend.authorization.dto.UserDto;
import com.torre.backend.authorization.entities.Role;
import com.torre.backend.authorization.entities.User;
import com.torre.backend.authorization.enums.StatusEnum;
import com.torre.backend.authorization.mappers.UserMapper;
import com.torre.backend.authorization.repositories.RoleRepository;
import com.torre.backend.authorization.repositories.UserRepository;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.exceptions.BaseException;
import com.torre.backend.common.utils.PageableMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Objects;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final SecureRandom random = new SecureRandom();
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public void createUser(CreateUserDto userDto, String username) {
        if (userDto == null) throw new BaseException(HttpStatus.BAD_REQUEST, "Errores de validación");
        Role role = roleRepository.findById(userDto.getRoleId()).orElse(null);
        if(role == null) throw new BaseException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        User user = UserMapper.createEntity(userDto, role);
        user.setCreatedBy(username);
        int length =12;
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        log.info("Contraseña generada para el usuario {}: {}", userDto.getUsername(), password.toString());
        user.setPassword(bCryptPasswordEncoder.encode(password.toString()));
        this.userRepository.save(user);

    }

    public Page<UserDto> findAll(QueryParamsDto queryParamsDto) {
        String filter = queryParamsDto.getFilter();
        filter = "%" + filter + "%";
        Pageable page = PageableMapper.buildPage(queryParamsDto);
        Page<User> userPage = userRepository.filterUser(filter, page);
        return userPage.map(UserMapper::toDto);
    }

    public void updateUser(Long id, CreateUserDto userDto, String username) {
        if(userDto == null) throw new BaseException(HttpStatus.BAD_REQUEST, "Errores de validación");
        Role role = roleRepository.findById(userDto.getRoleId()).orElse(null);
        if(role == null) throw new BaseException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new BaseException(HttpStatus.NOT_FOUND, "User no encontrado");
        UserMapper.updateEntity(user, userDto, role);
        user.setUpdatedBy(username);
        userRepository.save(user);
    }

    public void updateUserStatus(Long id, StatusEnum statusEnum, String username) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new BaseException(HttpStatus.NOT_FOUND,"Category not found");
        user.setStatus(statusEnum);
        user.setUpdatedBy(username);
        userRepository.save(user);
    }

    public void updatePassword(Long id, UpdatePasswordDto passwordDto, String username) {
        if(passwordDto == null) throw new BaseException(HttpStatus.BAD_REQUEST, "Errores de validación");
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new BaseException(HttpStatus.NOT_FOUND, "User no encontrado");
        if (!bCryptPasswordEncoder.matches(passwordDto.getPassword(), user.getPassword())) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, "La contraseña actual es incorrecta");
        }
        if (!Objects.equals(passwordDto.getNewPassword(), passwordDto.getConfirmPassword())) throw new BaseException(HttpStatus.NOT_FOUND, "Las nuevas contraseñas no coinciden");
        passwordDto.setNewPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
        UserMapper.updatePassword(user, passwordDto);
        user.setUpdatedBy(username);
        userRepository.save(user);
    }
}

