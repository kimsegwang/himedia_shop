package com.example.tobi.himedia_shop.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Products {
    private int id;
    private String category;
    private String sellerId;
    private String title;
    private String content;
    private String contentImg;
    private String created;
    private String updated;
    private int stock;
    private int price;
    private int temperature;
    private String precipitation;


}
