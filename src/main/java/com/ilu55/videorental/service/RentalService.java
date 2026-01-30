package com.ilu55.videorental.service;

import com.ilu55.videorental.entity.Rental;
import com.ilu55.videorental.entity.User;
import com.ilu55.videorental.entity.Video;
import com.ilu55.videorental.repository.RentalRepository;
import com.ilu55.videorental.repository.UserRepository;
import com.ilu55.videorental.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Rents a video for a specific user.
     * Enforces the limit of 2 active rentals.
     */
    @Transactional
    public Rental rentVideo(String email, Long videoId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 1. Check active rental limit 
        long activeCount = rentalRepository.countByUserAndReturnedFalse(user);
        if (activeCount >= 2) {
            throw new IllegalStateException("You cannot have more than 2 active rentals at a time.");
        }

        // 2. Check video existence and availability [cite: 28, 41]
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found with ID: " + videoId));

        if (video.getIsAvailable() == null || !video.getIsAvailable())
            throw new IllegalStateException("Video '" + video.getTitle() + "' is currently not available for rent.");
         

        // 3. Update video status and create rental record [cite: 28, 37]
        video.setIsAvailable(false);
        videoRepository.save(video);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setVideo(video);
        rental.setRentalDate(LocalDateTime.now());
        rental.setReturned(false);

        return rentalRepository.save(rental);
    }

    /**
     * Returns a rented video and updates availability[cite: 35, 38].
     */
    @Transactional
    public void returnVideo(String email, Long videoId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Rental rental = rentalRepository.findByUserAndVideoIdAndReturnedFalse(user, videoId)
                .orElseThrow(() -> new EntityNotFoundException("Active rental record not found for this video."));

        // Update rental status
        rental.setReturned(true);
        rentalRepository.save(rental);

        // Make video available again [cite: 28]
        Video video = rental.getVideo();
        video.setIsAvailable(true);
        videoRepository.save(video);
    }
}
