package com.ilu55.videorental.repository;

import com.ilu55.videorental.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    
    // Custom query to find videos that are available for rent [cite: 24, 25]
    List<Video> findByIsAvailableTrue();
}