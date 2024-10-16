package com.torre.backend.authorization.services;

import com.torre.backend.authorization.entities.User;
import com.torre.backend.authorization.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }
}
