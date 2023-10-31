package com.dpap.bookingapp.notification;

import java.time.LocalDateTime;

public class NotificationTemplate {

    private final String name;
    private final Long reservationId;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public NotificationTemplate(String name, Long reservationId, LocalDateTime from, LocalDateTime to) {
        this.name = name;
        this.reservationId = reservationId;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
