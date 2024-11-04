package com.example.tobi.himedia_shop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Builder
@Setter
public class Payment {
    private int id;
    private String userId;
    private int deposit;
    private int withdrawal;
    private int balance;
    private Date paymentDate;
    private boolean isPurchased;
}
