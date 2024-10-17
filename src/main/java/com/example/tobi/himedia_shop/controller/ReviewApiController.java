package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.model.Review;
import com.example.tobi.himedia_shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/reveiw")
    public ResponseEntity<?> reviewInsert(@RequestBody Review review) {
        reviewService.InsertReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 성공적으로 추가되었습니다.");
    }

    @GetMapping("/reveiw")
    public List<Review> reviewList(@RequestParam int productId) {
        List<Review> reviews= reviewService.GetAllReviews(reviewId);
        return reviews;
    }
    @DeleteMapping("/review")
    public ResponseEntity<?> reviewDelete(@RequestBody int reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 성공적으로 삭제되었습니다.");
    }

}
