package com.polling.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.polling.model.User;
import com.polling.repository.UserRepository;
import com.polling.utility.JwtUtil;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Registered Successfully";
    }

    public String loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            // Retrieve the roles of the user as a comma-separated string
            String roles = user.get().getRoles().stream()
                                .map(role -> role.name())  // Convert each Role to its name (string)
                                .collect(Collectors.joining(","));  // Join them with commas
            // Generate token with both username and roles
            return jwtUtil.generateToken(username, roles);
        }
        return "Invalid Credentials";
    }
}
