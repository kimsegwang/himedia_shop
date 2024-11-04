package com.example.tobi.himedia_shop.dto.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentBalanceRequestDTO {
    private String userId;
    private int deposit;
    private int withdrawal;
    private int balance;
    private int isPurchased;
}
