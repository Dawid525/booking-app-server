package com.dpap.bookingapp.booking.place.dataaccess;

import com.dpap.bookingapp.booking.facility.FacilityType;
import com.dpap.bookingapp.booking.place.PlaceCategory;
import com.dpap.bookingapp.booking.place.room.dto.RoomDTO;

import java.util.List;

public record PlaceResponse(
        Long id,
        String name,
        String description,
        Address address,
        PlaceCategory placeCategory,
        Long userId,
        Integer minPricePerNight,
        List<RoomDTO> rooms,
        List<FacilityType> facilities
) {

    public PlaceResponse withRooms(List<RoomDTO> rooms) {
        return new PlaceResponse(id, name, description, address, placeCategory, userId, minPricePerNight, rooms, facilities);
    }
}
