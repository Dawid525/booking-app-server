package com.dpap.bookingapp.booking.accomodation;


import com.dpap.bookingapp.booking.place.room.Room;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record Accommodation(
        Long id,
        Long userId,
        Long placeId,
        ZonedDateTime start,
        ZonedDateTime end,
        BigDecimal value,
        List<Room> rooms) {
}
