package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.product.review.ReviewQueryDTO;
import com.example.tobi.himedia_shop.dto.product.review.ReviewResponseDTO;
import com.example.tobi.himedia_shop.model.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.awt.print.Pageable;
import java.util.List;

@Mapper
public interface ReviewMapper {
    int reviewInsert(Review review);
    int reviewDelete(int reviewId);
    int countReviews(int productId);

    List<Review> getAllReviews(ReviewQueryDTO query);



    void reviewInserta(List<Review> reviews); // 벌크 삽입

    List<Review> getAllReviewsa(); // 모든 리뷰 조회
}
