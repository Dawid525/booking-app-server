package com.dpap.bookingapp.availability.service;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "availability")
@Entity
public class Availability {

    @Id
    @SequenceGenerator(name = "availabilities_id_seq", sequenceName = "seq_availabilities",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "availabilities_id_seq")
    private Long id;
    private Long objectId;
    private LocalDateTime start;
    private LocalDateTime finish;
    private LocalDateTime at;

    public Availability(Long id, Long objectId, LocalDateTime start, LocalDateTime finish, LocalDateTime at) {
        this.id = id;
        this.objectId = objectId;
        this.start = start;
        this.finish = finish;
        this.at = at;
    }

    public Availability(Long objectId, LocalDateTime start, LocalDateTime finish, LocalDateTime at) {
        this.objectId = objectId;
        this.start = start;
        this.finish = finish;
        this.at = at;
    }

    public Availability() {

    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long referenceId) {
        this.objectId = referenceId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public void setAt(LocalDateTime at) {
        this.at = at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
