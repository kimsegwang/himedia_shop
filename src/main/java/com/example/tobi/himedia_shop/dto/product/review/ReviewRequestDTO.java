package com.example.tobi.himedia_shop.dto.product.review;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ReviewRequestDTO {
    private String review;
    private String productId;
    private String userId;
    private String title;
    private float rating;
    private MultipartFile reviewImage;
}
