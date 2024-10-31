package com.example.tobi.himedia_shop.controller.prduct;


import com.example.tobi.himedia_shop.dto.history.PurchaseHistoryWithProductDTO;
import com.example.tobi.himedia_shop.model.Member;
import com.example.tobi.himedia_shop.model.PurchaseHistory;
import com.example.tobi.himedia_shop.service.product.PurchaseHistoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    @GetMapping("/history")
    public String history(HttpSession session, Model model) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userId");

        // 사용자 ID를 사용하여 구매 내역 조회
        List<PurchaseHistoryWithProductDTO> history = purchaseHistoryService.getPurchaseHistoryWithProduct(userId);

        // 조회된 구매 내역을 모델에 추가
        model.addAttribute("history", history);

        // 뷰 이름 반환 (예: "purchaseHistory") - 실제 뷰 이름에 맞게 수정
        return "purchaseHistory"; // 해당 뷰를 표시하는 이름
    }
}
