package com.ilu55.videorental.service;

import com.ilu55.videorental.dto.UserRegistrationDto;
import com.ilu55.videorental.entity.User;
import com.ilu55.videorental.exception.EmailAlreadyExistsException;
import com.ilu55.videorental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Registration data is missing");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (dto.getFirstName() == null || dto.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (dto.getLastName() == null || dto.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }

        // Ensure email is unique before saving [cite: 16]
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + dto.getEmail());
        }

        // Build User entity with hashed password [cite: 17, 18]
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                // Default to CUSTOMER if no role is provided [cite: 19]
                .role(dto.getRole() == null || dto.getRole().isEmpty() ? "CUSTOMER" : dto.getRole())
                .build();

        try {
            User saved = userRepository.save(user);
            if (saved == null || saved.getId() == null) {
                throw new IllegalStateException("User was not saved");
            }
            return saved;
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new IllegalStateException("Database error while saving user", ex);
        }
    }
}
