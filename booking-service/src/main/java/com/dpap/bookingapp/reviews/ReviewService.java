package com.dpap.bookingapp.reviews;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void createReview(String content, Long reservationId, Long userId, Rating rating) {
        Review review = new Review(content, rating, reservationId, userId, LocalDateTime.now());
        reviewRepository.save(review);
    }

    public List<Review> getReviewsForPlace(Long reservationId) {
        return reviewRepository.findAllByPlaceId(reservationId);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        var review = findByIdAndUserId(reviewId, userId);
        reviewRepository.deleteById(reviewId);
    }

    private Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Not found review with id:" + reviewId));
    }

    private Review findByIdAndUserId(Long reviewId, Long userId) {
        return reviewRepository.findByIdAndUserId(reviewId, userId).orElseThrow(() -> new RuntimeException("Not found review with id:" + reviewId));
    }

}
