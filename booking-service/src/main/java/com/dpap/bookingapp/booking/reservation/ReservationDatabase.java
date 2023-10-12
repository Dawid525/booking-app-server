package com.dpap.bookingapp.booking.reservation;

import com.dpap.bookingapp.booking.place.room.dto.RoomId;
import io.vavr.control.Option;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationDatabase {
    List<Reservation> findAll();

    Option<Reservation> findById(Long id);

    List<Reservation> getByRoomId(RoomId roomId, LocalDateTime from, LocalDateTime to);

    void save(Reservation reservation);

    void updateState(Long reservationId, ReservationState state);
}
