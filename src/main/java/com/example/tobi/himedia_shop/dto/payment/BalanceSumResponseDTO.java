package com.example.tobi.himedia_shop.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceSumResponseDTO {
    private int totalDeposit;
    private int totalWithdrawal;
    private int totalWithdrawalPurchased;
}
