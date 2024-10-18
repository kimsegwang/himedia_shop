package com.example.tobi.himedia_shop.dto.product.review;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewResponseDTO {

    private int id;
    private int productId;
    private String userId;
    private String title;
    private String review;
    private String reviewDate;
    private float score;
}
