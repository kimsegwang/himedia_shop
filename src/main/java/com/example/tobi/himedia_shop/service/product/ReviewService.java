package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.review.RequestReviewDTO;
import com.example.tobi.himedia_shop.dto.product.review.ReviewResponseDTO;
import com.example.tobi.himedia_shop.mapper.ReviewMapper;
import com.example.tobi.himedia_shop.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;

    public boolean InsertReview(RequestReviewDTO requestDTO) {
        Review review = Review.builder()
                .userId(requestDTO.getUserId())
                .title(requestDTO.getTitle())
                .review(requestDTO.getReview())
                .productId(requestDTO.getProductId())
                .score(requestDTO.getRating())
                .build();
        return reviewMapper.reviewInsert(review) > 0;
    }
    public List<ReviewResponseDTO> GetAllReviews(int productId) {
        List<Review> reviews = reviewMapper.reviewSelect(productId);

        return reviews.stream()
                .map(review -> ReviewResponseDTO.builder()
                        .id(review.getId())
                        .reviewDate(review.getReviewDate())
                        .userId(review.getUserId())
                        .productId(productId)
                        .score(review.getScore())
                        .title(review.getTitle())
                        .review(review.getReview())
                        .build())
                .collect(Collectors.toList());

    }

    public boolean deleteReview(int reviewId) {
        return reviewMapper.reviewDelete(reviewId)>0;
    }
}
