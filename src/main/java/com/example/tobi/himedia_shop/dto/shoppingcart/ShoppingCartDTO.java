package com.example.tobi.himedia_shop.dto.shoppingcart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {
    private String img;
    private String title;
    private int price;
    private String userId;
    private int productId;
}
