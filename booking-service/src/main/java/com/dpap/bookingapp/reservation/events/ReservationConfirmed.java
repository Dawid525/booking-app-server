package com.dpap.bookingapp.reservation.events;

import com.dpap.bookingapp.reservation.Reservation;
import org.springframework.context.ApplicationEvent;

public class ReservationConfirmed extends ApplicationEvent implements ReservationEvent {

    private Reservation reservation;

    public ReservationConfirmed(Object source, Reservation reservation) {
        super(source);
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
