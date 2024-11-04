package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.payment.BalanceResponseDTO;
import com.example.tobi.himedia_shop.dto.payment.PaymentBalanceRequestDTO;
import com.example.tobi.himedia_shop.dto.product.buy.BalanceCheckResult;
import com.example.tobi.himedia_shop.dto.product.buy.BuyProductRequestDTO;
import com.example.tobi.himedia_shop.dto.product.buy.StockUpdateDTO;
import com.example.tobi.himedia_shop.mapper.PaymentMapper;
import com.example.tobi.himedia_shop.mapper.ProductBuyMapper;
import com.example.tobi.himedia_shop.mapper.ProductMapper;
import com.example.tobi.himedia_shop.model.Products;
import com.example.tobi.himedia_shop.model.PurchaseHistory;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ProductBuyService {

    private final ProductBuyMapper productBuyMapper;
    private final ProductMapper productMapper;
    private final PaymentMapper paymentMapper;
    private final Lock lock = new ReentrantLock();

    public boolean buyProduct(BalanceCheckResult balanceCheckResult, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        lock.lock();
        try {
            // BuyProductRequestDTO를 변수에 저장
            BuyProductRequestDTO requestDTO = balanceCheckResult.getBuyProductRequestDTO();

            Products productById = productMapper.getProductById(requestDTO.getProductId());
            if (productById == null) {
                return false; // 또는 예외를 던지기
            }

            int updatedStock = productById.getStock() - requestDTO.getStock();
            if (updatedStock < 0) {
                return false;
            }

            BalanceResponseDTO balanceResponseDTO = paymentMapper.UserBalance(userId);
            int totalCost = requestDTO.getPrice() * requestDTO.getStock();

            paymentMapper.addPayment(PaymentBalanceRequestDTO.builder()
                    .balance(balanceResponseDTO.getBalance())
                    .isPurchased(1)
                    .withdrawal(totalCost)
                    .deposit(0)
                    .userId(userId)
                    .build());

            int purchaseResult = productBuyMapper.buyProduct(
                    PurchaseHistory.builder()
                            .productId(requestDTO.getProductId())
                            .price(requestDTO.getPrice())
                            .purchaseVolume(requestDTO.getStock())
                            .userId(userId)
                            .build()
            );

            if (purchaseResult > 0) {
                // 재고 업데이트
                StockUpdateDTO stockUpdateDTO = StockUpdateDTO.builder()
                        .stock(updatedStock)
                        .productId(requestDTO.getProductId())
                        .build();

                productBuyMapper.updateProduct(stockUpdateDTO);
                return true; // 성공적으로 구매 완료
            }
            return false; // 구매 실패
        } finally {
            // Lock을 해제하여 다른 사용자가 접근할 수 있도록 함
            lock.unlock();
        }
    }


    public BalanceCheckResult checkProductBalance(BuyProductRequestDTO buyProductRequestDTO, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        BalanceResponseDTO balanceResponseDTO = paymentMapper.UserBalance(userId);

        if (balanceResponseDTO == null) {
            return BalanceCheckResult.builder()
                    .sufficientFunds(false)
                    .totalCost(0)
                    .buyProductRequestDTO(null)
                    .build();
        }

        int totalCost = buyProductRequestDTO.getPrice() * buyProductRequestDTO.getStock();
        boolean sufficientFunds = balanceResponseDTO.getBalance() >= totalCost;

        return BalanceCheckResult.builder()
                .sufficientFunds(sufficientFunds)
                .totalCost(totalCost)
                .buyProductRequestDTO(buyProductRequestDTO)
                .build();
    }

}
