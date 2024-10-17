package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.dto.ProductDetailResponseDTO;
import com.example.tobi.himedia_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;
    @PostMapping("/detail")
    public ProductDetailResponseDTO productDetail(
            @PathVariable("productId") String productId
    ) {
        return productService.getProductById(productId);
    }
}
