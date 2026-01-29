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
@Table(name = "videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // [cite: 23]

    @Column(nullable = false)
    private String director; // [cite: 23]

    @Column(nullable = false)
    private String genre; // [cite: 23]

   
    @Column(nullable = false)
    @Builder.Default
    private boolean isAvailable = true; // [cite: 23, 24]
}
