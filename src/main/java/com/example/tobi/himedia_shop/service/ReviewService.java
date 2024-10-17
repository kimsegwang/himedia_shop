package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.mapper.ReviewMapper;
import com.example.tobi.himedia_shop.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;

    public void InsertReview(Review review) {
        reviewMapper.reviewInsert(review);
    }
    public List<Review> GetAllReviews(int productId) {
        List<Review> reviews = reviewMapper.reviewSelect(productId);
        return reviews;
    }

    public void deleteReview(int reviewId) {
        reviewMapper.reviewDelete(reviewId);
    }
}
