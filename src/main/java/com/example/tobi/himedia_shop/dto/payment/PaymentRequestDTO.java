package com.example.tobi.himedia_shop.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {
    private String userId;
    private int deposit;
    private int withdrawal;
}
