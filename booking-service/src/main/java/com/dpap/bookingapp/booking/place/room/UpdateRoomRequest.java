package com.dpap.bookingapp.booking.place.room;

import com.dpap.bookingapp.booking.facility.FacilityType;

import java.util.List;

public record UpdateRoomRequest(
        Integer capacity,
        RoomState state,
        Long pricePerNight,
        String description,
        String name,
        List<FacilityType> facilities
) {
}
