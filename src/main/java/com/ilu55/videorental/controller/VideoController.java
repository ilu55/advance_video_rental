package com.ilu55.videorental.controller;

import com.ilu55.videorental.entity.Video;
import com.ilu55.videorental.service.RentalService;
import com.ilu55.videorental.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private RentalService rentalService;

    /**
     * Browse the list of available videos.
     * Accessible by any authenticated CUSTOMER or ADMIN[cite: 25].
     */
    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    /**
     * Add a new video.
     * Restricted to ADMIN role only[cite: 26].
     */
    @PostMapping
    public ResponseEntity<Video> createVideo(@RequestBody Video video) {
        Video savedVideo = videoService.saveVideo(video);
        return new ResponseEntity<>(savedVideo, HttpStatus.CREATED);
    }

    /**
     * Update video details.
     * Restricted to ADMIN role only[cite: 26].
     */
    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @RequestBody Video videoDetails) {
        Video updatedVideo = videoService.updateVideo(id, videoDetails);
        return ResponseEntity.ok(updatedVideo);
    }

    /**
     * Delete a video.
     * Restricted to ADMIN role only[cite: 26].
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{videoId}/rent") // Rent endpoint [cite: 37]
    public ResponseEntity<?> rent(@PathVariable Long videoId, Authentication auth) {
        return ResponseEntity.ok(rentalService.rentVideo(auth.getName(), videoId));
    }

    @PostMapping("/{videoId}/return") // Return endpoint [cite: 38]
    public ResponseEntity<?> returnVid(@PathVariable Long videoId, Authentication auth) {
        rentalService.returnVideo(auth.getName(), videoId);
        return ResponseEntity.ok("Returned successfully");
    }
}