package com.example.tobi.himedia_shop.dto.product.review;

import com.example.tobi.himedia_shop.dto.product.review.ReviewResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class PageResponseDTO {
    private List<ReviewResponseDTO> reviews;
    private int totalReviews;
    private int totalPages;


}