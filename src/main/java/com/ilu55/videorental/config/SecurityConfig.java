package com.ilu55.videorental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabled for simplicity in REST APIs
                .authorizeHttpRequests(auth -> auth
                        // 1. Public Endpoints: No authentication needed to access these
                        .requestMatchers("/api/register", "/api/login").permitAll()

                        // 2. Video Browsing: Any authenticated user can access [cite: 25]
                        .requestMatchers(HttpMethod.GET, "/api/videos/**").authenticated()

                        // 3. Admin Endpoints: Only ADMIN can Create, Update, or Delete [cite: 26]
                        .requestMatchers(HttpMethod.POST, "/api/videos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/videos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/videos/**").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .httpBasic(basic -> {
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
