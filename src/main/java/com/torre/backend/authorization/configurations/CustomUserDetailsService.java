package com.torre.backend.authorization.configurations;

import com.torre.backend.authorization.entities.User;
import com.torre.backend.authorization.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info("{}", user);
        if( user != null) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
            //roles = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole().name())).toList();
            logger.info("{}", roles);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found");
    }
}
