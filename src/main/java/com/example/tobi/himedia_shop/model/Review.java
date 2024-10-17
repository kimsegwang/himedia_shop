package com.example.tobi.himedia_shop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Review {
    private int id;
    private String productId;
    private String review;
    private String reviewDate;
    private int score;
}
