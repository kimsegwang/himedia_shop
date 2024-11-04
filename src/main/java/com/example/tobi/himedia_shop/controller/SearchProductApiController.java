package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.model.SearchProduct;
import com.example.tobi.himedia_shop.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchProductApiController {

    private final SearchService searchService;

    @GetMapping("/api/products/search")
    public List<SearchProduct> searchProduct(
            @RequestParam("keyword") String keyword) {
        return searchService.searchProduct(keyword);
    }

}
