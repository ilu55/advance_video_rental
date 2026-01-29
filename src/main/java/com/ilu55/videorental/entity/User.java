package com.ilu55.videorental.entity;

 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // Used for Login (Basic Auth) [cite: 16, 20]

    @Column(nullable = false)
    private String password; // Must be hashed using BCrypt 

    @Column(nullable = false)
    private String firstName;// [cite: 18]

    @Column(nullable = false)
    private String lastName; // [cite: 18]

    @Column(nullable = false)
    private String role; // Defaults to CUSTOMER if not specified [cite: 9, 19]
}