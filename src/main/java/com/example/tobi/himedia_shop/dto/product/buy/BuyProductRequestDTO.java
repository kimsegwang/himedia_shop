package com.example.tobi.himedia_shop.dto.product.buy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyProductRequestDTO {
    private int productId;
    private String userId;
    private int price;
    private int stock;

}
