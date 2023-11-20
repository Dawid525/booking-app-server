package com.dpap.bookingapp.notification;

import java.time.LocalDateTime;

public class NotificationTemplate {

    private final String content;
    private final Long reservationId;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public NotificationTemplate(String content, Long reservationId, LocalDateTime from, LocalDateTime to) {
        this.content = content;
        this.reservationId = reservationId;
        this.from = from;
        this.to = to;
    }

    public String getContent() {
        return content;
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
