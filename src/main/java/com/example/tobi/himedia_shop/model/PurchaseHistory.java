package com.example.tobi.himedia_shop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class PurchaseHistory {
    private int productId;
    private String userId;
    private int price;
    private int purchaseVolume;
    private int id;
}
