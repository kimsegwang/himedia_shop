package com.example.tobi.himedia_shop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Review {
    private int id;
    private String userId;
    private String productId;
    private String title;
    private String review;
    private String reviewDate;
    private float score;
    private String reviewImg;
}
