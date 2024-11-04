package com.example.tobi.himedia_shop.dto.product.buy;


import com.example.tobi.himedia_shop.dto.payment.BalanceResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BalanceCheckResult {
    private boolean sufficientFunds;
    private double totalCost;
    private BuyProductRequestDTO buyProductRequestDTO;


}