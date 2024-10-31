package com.example.tobi.himedia_shop.dto.product.wishlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistRequestDTO {
    String userId;
    boolean  favorite;
    String productId;
}
