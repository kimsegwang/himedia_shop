package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.payment.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<BalanceResponseDTO> listUserBalance();
    BalanceResponseDTO UserBalance(String userId);
    BalanceSumResponseDTO financialSummary();
    List<SalesDataDTO> findDailySalesData();
    int addPayment(PaymentBalanceRequestDTO paymentBalanceRequestDTO );
}
