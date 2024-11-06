package com.example.tobi.himedia_shop.controller.product;


import com.example.tobi.himedia_shop.dto.history.PurchaseHistoryWithProductDTO;
import com.example.tobi.himedia_shop.service.product.PurchaseHistoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    @GetMapping("/history")
    public String history(            HttpSession session,
                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                      Model model) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userId");

        // 사용자 ID를 사용하여 구매 내역 조회
        List<PurchaseHistoryWithProductDTO> history = purchaseHistoryService.getPurchaseHistoryWithProduct(userId, page, pageSize);
        int allpage = purchaseHistoryService.getPurchaseHistory(userId);
        int totalRecords = allpage;

        int totalPages = (int) Math.ceil( totalRecords / pageSize);
        // 조회된 구매 내역을 모델에 추가
        model.addAttribute("history", history);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

        // 뷰 이름 반환 (예: "purchaseHistory") - 실제 뷰 이름에 맞게 수정
        return "purchaseHistory"; // 해당 뷰를 표시하는 이름
    }
}
