package com.example.tobi.himedia_shop.dto.product.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewQueryDTO {
    private int productId;
    private int limit;
    private int offset;


}
