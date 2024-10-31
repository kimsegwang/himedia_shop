package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.buy.BuyProductRequestDTO;
import com.example.tobi.himedia_shop.dto.product.buy.StockUpdateDTO;
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
    private final Lock lock = new ReentrantLock();

    public boolean buyProduct(BuyProductRequestDTO buyProductRequestDTO, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        lock.lock();
        try {

            Products productById = productMapper.getProductById(buyProductRequestDTO.getProductId());
            if (productById == null) {
                System.out.println("제품이 존재하지 않습니다.");
                return false; // 또는 예외를 던지기
            }

            int updatedStock  = productById.getStock()-buyProductRequestDTO.getStock();
            System.out.println(buyProductRequestDTO.getProductId()+"a"+updatedStock);
            if (updatedStock < 0) {
                return false;
            }
            int purchaseResult  = productBuyMapper.buyProduct(
                    PurchaseHistory.builder()
                            .productId(buyProductRequestDTO.getProductId())
                            .price(buyProductRequestDTO.getPrice())
                            .purchaseVolume(buyProductRequestDTO.getStock())
                            .userId(userId)
                            .build()
            );
            if (purchaseResult > 0) {
                // 재고 업데이트
                StockUpdateDTO stockUpdateDTO  =
                        StockUpdateDTO.builder()
                        .stock(updatedStock)
                        .productId(buyProductRequestDTO.getProductId())
                        .build();

                productBuyMapper.updateProduct(stockUpdateDTO);
                return true; // 성공적으로 구매 완료
            }
            return false; // 구매 실패
        }finally {
            // Lock을 해제하여 다른 사용자가 접근할 수 있도록 함
            lock.unlock();
        }

    }
}
