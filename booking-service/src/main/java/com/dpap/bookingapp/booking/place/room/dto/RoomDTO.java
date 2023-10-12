package com.dpap.bookingapp.booking.place.room.dto;

import com.dpap.bookingapp.booking.facility.FacilityType;
import com.dpap.bookingapp.booking.place.room.RoomState;

import java.util.List;

public record RoomDTO(
        Long id,
        Integer capacity,
        RoomState state,
        Long placeId,
        Long pricePerNight,
        String description,
        String name,
        List<FacilityType> facilities
) {
}
