package com.dpap.bookingapp.booking.reservation.dto;

import com.dpap.bookingapp.booking.reservation.ReservationState;

import java.time.LocalDateTime;
import java.util.Optional;

public class SearchReservationFilter {

    private Long userId;
    private Long roomId;
    private Long placeId;
    private LocalDateTime from;
    private LocalDateTime to;
    private ReservationState state;

    public SearchReservationFilter(Long userId, Long placeId, Long roomId, ReservationState state, LocalDateTime from, LocalDateTime to) {
        this.userId = userId;
        this.placeId = placeId;
        this.state = state;
        this.roomId = roomId;
        this.from = from;
        this.to = to;
    }

    public boolean isEmpty() {
        return getState().isEmpty() && getPlaceId().isEmpty() && getUserId().isEmpty() && getFrom().isEmpty() && getTo().isEmpty() && getRoomId().isEmpty();
    }


    public Optional<Long> getUserId() {
        return Optional.ofNullable(userId);
    }

    public Optional<Long> getRoomId() {
        return Optional.ofNullable(roomId);
    }

    public Optional<Long> getPlaceId() {
        return Optional.ofNullable(placeId);
    }

    public Optional<ReservationState> getState() {
        return Optional.ofNullable(state);
    }

    public Optional<LocalDateTime> getFrom() {
        return Optional.ofNullable(from);
    }

    public Optional<LocalDateTime> getTo() {
        return Optional.ofNullable(to);
    }
}
