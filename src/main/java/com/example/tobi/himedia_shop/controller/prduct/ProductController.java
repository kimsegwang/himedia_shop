package com.example.tobi.himedia_shop.controller.prduct;

import com.example.tobi.himedia_shop.dto.product.product.ProductDetailResponseDTO;
import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;
import com.example.tobi.himedia_shop.service.product.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    @GetMapping("/detail/{productId}")
    public String productDetail(Model model, @PathVariable("productId") int productId, HttpSession session) {
        ProductDetailResponseDTO productById = productService.getProductById(productId);
        model.addAttribute("productById", productById);

        return "product/product-detail";
    }
    @GetMapping("/detail/recommendations")
    public ResponseEntity<List<ProductListResponseDTO>> getAllProducts(HttpSession session) {
        List<ProductListResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
