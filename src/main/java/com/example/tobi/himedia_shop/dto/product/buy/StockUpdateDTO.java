package com.example.tobi.himedia_shop.dto.product.buy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockUpdateDTO {
    private int productId;
    private int stock;
}
