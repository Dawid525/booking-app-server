package com.dpap.bookingapp.booking.place.room.dto;

import com.dpap.bookingapp.booking.facility.FacilityType;

import java.util.List;

public record RoomDetailsDTO(
        Long id,
        Long roomId,
        String name,
        String description,
        String imageUrl,
        List<FacilityType> facilities
) {
}
