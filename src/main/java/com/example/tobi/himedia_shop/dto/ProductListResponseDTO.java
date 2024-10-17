package com.example.tobi.himedia_shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductListResponseDTO {
    private int id;
    private String category;
    private String sellerId;
    private String title;
    private String content;
    private String created;
    private int stock;
    private String price;
    private String contentImg;
    private int temperature;
    private int precipitation;
}
