package com.dpap.bookingapp.booking.reservation;

import com.dpap.bookingapp.booking.place.room.dto.RoomId;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryReservationRepository implements ReservationDatabase {

    Map<Long, Reservation> reservations = new HashMap<>();
    private static Long ids = 0L;
    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public Option<Reservation> findById(Long id) {
        return Try
                .ofSupplier(
                        () -> reservations.get(id)
                ).toOption();

    }

    @Override
    public List<Reservation> getByRoomId(RoomId roomId, LocalDateTime from, LocalDateTime to) {
        return reservations.values()
                .stream()
                .filter(reservation -> reservation.getRoomId().equals(roomId))
                .filter(reservation -> reservation.getCheckIn().isAfter(from))
                .filter(reservation -> reservation.getCheckOut().isBefore(to))
                .toList();
    }

    @Override
    public void save(Reservation reservation) {
        reservations.put(ids,reservation);
        ids = ids + 1;
    }

    @Override
    public void updateState(Long reservationId, ReservationState state) {
        var reservation = reservations.get(reservationId);
        switch (state) {
            case CANCELED -> reservation.cancel(LocalDateTime.now());
            case CONFIRMED -> reservation.confirm();
            case CHECK_IN -> reservation.checkIn();
            case CHECK_OUT -> reservation.checkOut();
        }
        reservations.put(reservationId, reservation);
    }
}
