package com.example.tobi.himedia_shop.controller.product;

 
import com.example.tobi.himedia_shop.dto.ReviewMessageResponseDTO;
import com.example.tobi.himedia_shop.dto.product.review.PageResponseDTO;
import com.example.tobi.himedia_shop.dto.product.review.ReviewRequestDTO;
import com.example.tobi.himedia_shop.service.product.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<ReviewMessageResponseDTO> reviewInsert(@ModelAttribute ReviewRequestDTO requestReviewDTO) {
        boolean success = reviewService.insertReview(requestReviewDTO);
        return ResponseEntity.ok(new ReviewMessageResponseDTO(success ? "리뷰 작성 완료" : "리뷰 작성 실패"));
    }


    @GetMapping("/reviews")
    public PageResponseDTO reviewList(@RequestParam int productId,
                                      @RequestParam int page,
                                      @RequestParam int size) {

        return reviewService.getReviewsForProduct(productId, page, size);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<?> reviewDelete(@PathVariable int reviewId) {

        boolean success = reviewService.deleteReview(reviewId);
        if (success) {
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("리뷰 삭제에 실패했습니다.");
        }
    }



}
