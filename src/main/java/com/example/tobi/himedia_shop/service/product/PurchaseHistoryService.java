package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.history.PurchaseHistoryWithProductDTO;
import com.example.tobi.himedia_shop.mapper.HistoryMapper;
import com.example.tobi.himedia_shop.model.PurchaseHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {
    private final HistoryMapper historyMapper;

    @Autowired
    private HistoryMapper purchaseHistoryMapper;

    // 사용자 ID를 매개변수로 받아 해당 사용자의 구매 내역을 반환
    public List<PurchaseHistoryWithProductDTO> getPurchaseHistoryWithProduct(String userId) {
        return purchaseHistoryMapper.findPurchaseHistoryWithProductByUserId(userId);
    }

}
