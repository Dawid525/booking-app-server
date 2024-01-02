package com.dpap.bookingapp.reviews;

import java.time.LocalDateTime;

public record ReviewDto(int rating, String author, String content, Long placeId, String at) {
}
