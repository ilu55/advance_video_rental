package com.ilu55.videorental.controller;

import com.ilu55.videorental.dto.UserLoginDto;
import com.ilu55.videorental.dto.UserRegistrationDto;
import com.ilu55.videorental.entity.User;
import com.ilu55.videorental.exception.EmailAlreadyExistsException;
import com.ilu55.videorental.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Public endpoint for User Registration.
     * Captures Email, Password, First Name, Last Name, and Role[cite: 18].
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto registrationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(org.springframework.validation.FieldError::getField, org.springframework.validation.FieldError::getDefaultMessage));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            User registeredUser = userService.registerUser(registrationDto);
            if (registeredUser == null || registeredUser.getId() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed");
            }
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    /**
     * Endpoint for Login. 
     * Accessible via Basic Auth (Email and Password)[cite: 8, 20].
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",  "Email and Password are required"));
    }
         try {
        // 2. This replaces the header-based check. It looks up the user in DB and matches the password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        Map<String, String> response = new HashMap<>();
        response.put("email", authentication.getName());
        response.put("status", "Authenticated");
        // Returns the roles assigned to the user [cite: 13]
        response.put("roles", authentication.getAuthorities().toString());
        return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
        // 4. This triggers if email or password don't match the database
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid email or password"));
    }
    }
}