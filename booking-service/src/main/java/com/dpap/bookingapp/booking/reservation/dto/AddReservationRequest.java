package com.dpap.bookingapp.booking.reservation.dto;

import java.time.LocalDateTime;

public record AddReservationRequest(
        Long roomId,
        Long placeId,
        LocalDateTime start,
        LocalDateTime finish,
        LocalDateTime at
) { }