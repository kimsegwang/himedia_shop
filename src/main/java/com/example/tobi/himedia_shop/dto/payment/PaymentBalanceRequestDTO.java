package com.example.tobi.himedia_shop.dto.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentBalanceRequestDTO {
    private String userId;
    private int deposit; //입금
    private int withdrawal; // 출금
    private int balance; //처리된후 잔액
    private int isPurchased; //기본 0 물건 살시 1
}
