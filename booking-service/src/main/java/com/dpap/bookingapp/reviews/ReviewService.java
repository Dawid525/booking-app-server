package com.dpap.bookingapp.reviews;

import com.dpap.bookingapp.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    public void createReview(String content, Long placeId, Long userId, int rating) {
        Review review = new Review(content, rating, placeId, userId, LocalDateTime.now());
        reviewRepository.save(review);
    }

    private List<Review> getReviewsForPlace(Long placeId) {
        return reviewRepository.findAllByPlaceId(placeId);
    }

    public List<ReviewDto> getReviewDtosForPlace(Long placeId) {
        return reviewRepository.findAllByPlaceId(placeId)
                .stream().map(review -> new ReviewDto(
                        review.getRating(),
                        userService.findByUserId(review.getUserId()).get().getEmail(),
                        review.getContent(),
                        review.getPlaceId()
                ))
                .toList();
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
