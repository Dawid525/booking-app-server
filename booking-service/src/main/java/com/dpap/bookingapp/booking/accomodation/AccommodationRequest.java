package com.dpap.bookingapp.booking.accomodation;

import com.dpap.bookingapp.booking.place.dataaccess.Address;

import java.time.LocalDateTime;

public record AccommodationRequest(Address address, LocalDateTime from, LocalDateTime to, Integer capacity) {

}
