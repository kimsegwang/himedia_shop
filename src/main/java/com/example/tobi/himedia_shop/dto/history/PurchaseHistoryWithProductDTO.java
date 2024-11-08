package com.example.tobi.himedia_shop.dto.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryWithProductDTO {
    private String img;
    private String title;
    private int price;
    private int purchaseVolume;
    private String userId;
    private String reviewDate;


}