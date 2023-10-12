package com.dpap.bookingapp.booking.place;

import java.util.Optional;

public class RoomSearchFilter {
    private Long pricePerNight;
    private Integer capacity;

    public RoomSearchFilter(Long pricePerNight, Integer capacity) {
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
    }

    private RoomSearchFilter(Builder builder) {
        pricePerNight = builder.pricePerNight;
        capacity = builder.capacity;
    }

    public Optional<Long> getPricePerNight() {
        return Optional.ofNullable(pricePerNight);
    }

    public Optional<Integer> getCapacity() {
        return Optional.ofNullable(capacity);
    }

    public static final class Builder {
        private Long pricePerNight;
        private Integer capacity;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder pricePerNight(Long val) {
            pricePerNight = val;
            return this;
        }

        public Builder capacity(Integer val) {
            capacity = val;
            return this;
        }

        public RoomSearchFilter build() {
            return new RoomSearchFilter(this);
        }
    }
}