package com.dpap.bookingapp.booking.place;

import com.dpap.bookingapp.booking.place.dataaccess.Address;
import com.dpap.bookingapp.booking.place.room.dto.AddRoomRequest;

import java.util.List;

public record AddPlaceRequest(
        String name, String description, Address address, PlaceCategory category, List<AddRoomRequest> rooms) {
}
