package com.example.tobi.himedia_shop.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SalesDataDTO {
    private LocalDate paymentDate;
    private int totalDeposit;
    private int totalWithdrawal;

}
