package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.model.SearchProduct;
import com.example.tobi.himedia_shop.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;

    @GetMapping("/search-list")
    public List<SearchProduct>  searchProductsJson(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "page", defaultValue = "1") int page) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return  null;
        } else {
            List<SearchProduct> products = searchService.searchProduct(keyword,page);
            return  products;
        }
    }
    @GetMapping("/cartagory-list")
    public List<SearchProduct>  cartagoryProductsJson(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "page", defaultValue = "1") int page) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return  null;
        } else {
            List<SearchProduct> products = searchService.searchProduct(keyword,page);
            return  products;
        }
    }
}
