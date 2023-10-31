package com.dpap.bookingapp.booking.place;

import com.dpap.bookingapp.booking.place.dataaccess.Address;
import com.dpap.bookingapp.booking.place.room.dto.AddRoomRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AddPlaceRequest(
        @NotBlank(message = "Place name is mandatory")
        String name,
        @NotBlank(message = "Description is mandatory")
        String description,
        Address address,
        @NotNull
        PlaceCategory category,
        @NotEmpty
        List<AddRoomRequest> rooms
) {
}
