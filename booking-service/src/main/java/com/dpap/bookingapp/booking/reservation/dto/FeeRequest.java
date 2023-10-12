package com.dpap.bookingapp.booking.reservation.dto;

import java.math.BigDecimal;

public record FeeRequest(BigDecimal value, String reason) {
}
