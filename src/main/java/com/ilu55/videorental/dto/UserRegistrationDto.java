package com.ilu55.videorental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email; // [cite: 16, 18]

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password; // [cite: 16, 18]

    @NotBlank(message = "First name is required")
    private String firstName; // [cite: 18]

    @NotBlank(message = "Last name is required")
    private String lastName; // [cite: 18]

    private String role; // [cite: 16, 19]
} 
