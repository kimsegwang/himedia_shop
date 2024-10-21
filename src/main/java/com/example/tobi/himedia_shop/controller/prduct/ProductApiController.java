package com.example.tobi.himedia_shop.controller.prduct;

import com.example.tobi.himedia_shop.dto.product.buy.BuyProductRequestDTO;
import com.example.tobi.himedia_shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductApiController {
    private final ProductService productService;

    @PostMapping("/detail/api/buy")
    public ResponseEntity<?> buyProduct(@RequestBody BuyProductRequestDTO buyProductRequestDTO) {
        productService.buyProduct(buyProductRequestDTO);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}
