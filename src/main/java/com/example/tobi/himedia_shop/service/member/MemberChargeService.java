package com.example.tobi.himedia_shop.service.member;

import com.example.tobi.himedia_shop.dto.payment.BalanceResponseDTO;
import com.example.tobi.himedia_shop.dto.payment.PaymentBalanceRequestDTO;
import com.example.tobi.himedia_shop.mapper.admin.PaymentMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberChargeService {
    private final PaymentMapper paymentMapper;

    public BalanceResponseDTO getUsercharge(String userId) {
        BalanceResponseDTO balanceResponseDTO = paymentMapper.UserBalance(userId);
        if (balanceResponseDTO == null) {
            balanceResponseDTO = new BalanceResponseDTO();
            balanceResponseDTO.setBalance(0);
        }
        return balanceResponseDTO;
    }

    public boolean updateUserBalance(HttpSession session, int amount,String balance ) {
        String userid = (String) session.getAttribute("userId");
        return 0< paymentMapper.addPayment(PaymentBalanceRequestDTO.builder()
                .balance(Integer.parseInt(balance))
                .isPurchased(0)
                .withdrawal(amount)
                .deposit(0)
                .userId(userid)
                .build());

    }

    public boolean WithdrawalUserBalance(HttpSession session, int amount,String balance ) {
        String userid = (String) session.getAttribute("userId");
        return 0< paymentMapper.addPayment(PaymentBalanceRequestDTO.builder()
                .balance(Integer.parseInt(balance))
                .isPurchased(0)
                .withdrawal(0)
                .deposit(amount)
                .userId(userid)
                .build());

    }
}
