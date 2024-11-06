package com.example.tobi.himedia_shop.controller.product;


import com.example.tobi.himedia_shop.dto.history.PurchaseHistoryWithProductDTO;
import com.example.tobi.himedia_shop.dto.shoppingcart.ShoppingCartDTO;
import com.example.tobi.himedia_shop.service.product.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/ShoppingCart")
    public String ShoppingCart(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, Model model) {
        String userId = (String) session.getAttribute("userId");
        // 사용자 ID를 사용하여 구매 내역 조회
        List<ShoppingCartDTO> shoppingcart = shoppingCartService.getPurchaseHistoryWithProduct(userId, page, pageSize);
        int allpage = shoppingCartService.getPurchaseLike(userId);
        int totalRecords = allpage;
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        for (ShoppingCartDTO shoppingCartDTO : shoppingcart) {
            System.out.println(shoppingCartDTO);
            System.out.println(shoppingCartDTO.getProductId());
            System.out.println(shoppingCartDTO.getPrice());
        }

        // 조회된 구매 내역을 모델에 추가
        model.addAttribute("shoppingcart", shoppingcart);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

        // 뷰 이름 반환 (예: "purchaseHistory") - 실제 뷰 이름에 맞게 수정
        return "shoppingcart"; // 해당 뷰를 표시하는 이름

    }


}
