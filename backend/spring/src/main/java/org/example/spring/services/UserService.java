package org.example.spring.services;

import org.example.spring.entities.UserDto;
import org.example.spring.models.auth.User;
import org.example.spring.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUserAccount(UserDto accountDto) {
        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setEmail(accountDto.getEmail());
        user.setPassword(accountDto.getPassword());
        if (userRepository.existsByUsername(user.getUsername()) && userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User");
        }
        return userRepository.save(user);
    }

}