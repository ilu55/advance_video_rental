package com.ilu55.videorental.repository;

import com.ilu55.videorental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Used to verify identity during Basic Auth login [cite: 20]
    Optional<User> findByEmail(String email);
    
    // Used during registration to ensure email uniqueness [cite: 16]
    boolean existsByEmail(String email);
}