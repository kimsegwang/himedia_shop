package com.example.tobi.himedia_shop.controller.product;

import com.example.tobi.himedia_shop.dto.product.product.ProductDetailResponseDTO;
import com.example.tobi.himedia_shop.model.Products;
import com.example.tobi.himedia_shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/detail/api")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    // 구매 후 재고 조회 API
    @GetMapping("/stock/{productId}")
    public int getUpdatedStock(@PathVariable int productId) {
        // productId에 해당하는 상품의 최신 재고 정보를 반환
        ProductDetailResponseDTO product = productService.getProductById(productId);
        return product.getStock();  // 또는 재고 정보가 저장된 곳에서 가져옴
    }
}
