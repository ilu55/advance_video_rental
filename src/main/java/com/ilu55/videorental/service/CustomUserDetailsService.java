package com.ilu55.videorental.service;

import com.ilu55.videorental.entity.User;
import com.ilu55.videorental.repository.UserRepository;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

          
        // return org.springframework.security.core.userdetails.User
        //         .withUsername(user.getEmail())
        //         .password(user.getPassword())
        //         .roles(user.getRole())
        //         .build();
                return new org.springframework.security.core.userdetails.User(
    user.getEmail(),
    user.getPassword(),
    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
);
    }
}
