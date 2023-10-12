package com.dpap.bookingapp.booking.place.room.dto;

import com.dpap.bookingapp.booking.facility.FacilityType;
import com.dpap.bookingapp.booking.place.room.RoomState;

import java.util.List;

public record AddRoomRequest(
        String name,
        String description,
        Integer capacity,
        RoomState state,
        Long placeId,
        Long pricePerNight,
        List<FacilityType> facilities
){}
