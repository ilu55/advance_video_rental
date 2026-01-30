package com.ilu55.videorental.service;

import com.ilu55.videorental.entity.Video;
import com.ilu55.videorental.exception.ResourceNotFoundException;
import com.ilu55.videorental.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    // Any user can browse the list [cite: 25]
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    // Only the ADMIN is allowed to create [cite: 26]
    public Video saveVideo(Video video) {
        // All videos are assumed available to rent by default [cite: 24]
        video.setIsAvailable(true); 
        return videoRepository.save(video);
    }

    // Only the ADMIN is allowed to update [cite: 26]
    public Video updateVideo(Long id, Video videoDetails) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id)); // 

        video.setTitle(videoDetails.getTitle());
        video.setDirector(videoDetails.getDirector());
        video.setGenre(videoDetails.getGenre());
        video.setIsAvailable(videoDetails.getIsAvailable());

        return videoRepository.save(video);
    }

    // Only the ADMIN is allowed to delete [cite: 26]
    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Video not found with id: " + id); // 
        }
        videoRepository.deleteById(id);
    }
}