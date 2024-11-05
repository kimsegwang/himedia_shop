package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.model.Products;
import com.example.tobi.himedia_shop.model.SearchProduct;
import com.example.tobi.himedia_shop.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public String searchProducts(@RequestParam(value = "keyword", required = false
    ) String keyword, Model model) {
        if (keyword == null || keyword.trim().isEmpty()) {
            model.addAttribute("products", List.of());
        } else {
            List<SearchProduct> products = searchService.searchProduct(keyword);
            model.addAttribute("products", products);
        }
        model.addAttribute("searchKeyword", keyword);
        return "search";

    }


}

