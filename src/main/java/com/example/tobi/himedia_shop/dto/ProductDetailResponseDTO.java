package com.example.tobi.himedia_shop.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDetailResponseDTO {
    private int id;
    private String category;
    private String title;
    private String content;
    private String sellerId;
    private String created;
    private int price;
    private String img;
    private int stock;


}
