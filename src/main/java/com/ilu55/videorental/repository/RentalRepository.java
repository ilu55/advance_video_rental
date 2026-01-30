package com.ilu55.videorental.repository;

import com.ilu55.videorental.entity.Rental;
import com.ilu55.videorental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    long countByUserAndReturnedFalse(User user);
    Optional<Rental> findByUserAndVideoIdAndReturnedFalse(User user, Long videoId);
}
