package com.dpap.bookingapp.availability.service;

import com.dpap.bookingapp.timeslot.TimeSlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvailabilityService {


    private final AvailabilityRepository availabilityRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public boolean isObjectAvailable(Long objectId, TimeSlot timeSlot) {
        return availabilityRepository.
                findAllByObjectIdBetweenDates(objectId, timeSlot.getStart(), timeSlot.getEnd()).isEmpty();
    }

    public void reserveObject(Long objectId, TimeSlot timeSlot, LocalDateTime at) {
        availabilityRepository.save(
                new Availability(objectId, timeSlot.getStart(), timeSlot.getEnd(), timeSlot.getEnd())
        );
    }

    public List<TimeSlot> findAllReservedSlotsInPeriod(Long objectId) {
        return availabilityRepository.findAllByObjectId(objectId).stream()
                .map(availability -> new TimeSlot(availability.getStart(), availability.getFinish()))
                .toList();

    }

    @Transactional
    public void delete(Long objectId, TimeSlot timeSlot) {
        List<Availability> availabilities = availabilityRepository
                .findAllByObjectIdBetweenDates(objectId, timeSlot.getStart(), timeSlot.getEnd());
        availabilityRepository.deleteAll(availabilities);
    }
}
