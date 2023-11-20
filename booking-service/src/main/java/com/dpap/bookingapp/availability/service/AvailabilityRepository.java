package com.dpap.bookingapp.availability.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query(value = "SELECT * FROM availability a  WHERE a.object_id = :objectId AND a.start <= :finish AND a.finish >= :start",
            nativeQuery = true)
    List<Availability> findAllByObjectIdBetweenDates(Long objectId, LocalDateTime start, LocalDateTime finish);
    List<Availability> findAllByObjectId(Long objectId);
}
