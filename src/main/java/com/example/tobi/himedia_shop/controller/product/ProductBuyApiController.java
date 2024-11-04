package com.example.tobi.himedia_shop.controller.product;

import com.example.tobi.himedia_shop.dto.product.buy.BalanceCheckResult;
import com.example.tobi.himedia_shop.dto.product.buy.BuyProductRequestDTO;
import com.example.tobi.himedia_shop.service.product.ProductBuyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductBuyApiController {
    private final ProductBuyService productBuyService;

    @PostMapping("/detail/api/buy")
    public ResponseEntity<?> buyProduct(@RequestBody BuyProductRequestDTO buyProductRequestDTO, HttpSession session) {
        BalanceCheckResult balanceCheckResult = productBuyService.checkProductBalance(buyProductRequestDTO, session);

        if (!balanceCheckResult.isSufficientFunds()) {
            return ResponseEntity.badRequest().body("잔액이 부족합니다.");
        }

        if (productBuyService.buyProduct(balanceCheckResult, session)) {
            return ResponseEntity.ok("구매가 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("재고가 부족합니다.");
        }
    }
}
