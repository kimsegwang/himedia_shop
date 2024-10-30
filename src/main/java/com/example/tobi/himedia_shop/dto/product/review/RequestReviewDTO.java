package com.example.tobi.himedia_shop.dto.product.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestReviewDTO {
    private String review;
    private String productId;
    private String userId;
    private String title;
    private float rating;
}
