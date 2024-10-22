package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.model.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int reviewInsert(Review review);
    int reviewDelete(int reviewId);
    List<Review> reviewSelect(int productId);
}
