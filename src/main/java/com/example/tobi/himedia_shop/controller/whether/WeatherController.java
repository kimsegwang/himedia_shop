package com.example.tobi.himedia_shop.controller.whether;

import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;

import com.example.tobi.himedia_shop.service.WallPaperService;
import com.example.tobi.himedia_shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class WeatherController {
    private final ProductService productService;
    private final WallPaperService wallPaperService;

    @GetMapping
    public String Weather(Model model) {
        List<ProductListResponseDTO> allProducts = productService.getAllProducts();
        String weatherimg = wallPaperService.WeatherDivide();

        model.addAttribute("products", allProducts);
        model.addAttribute("weathering", weatherimg);
        return "connectionpage";
    }

}