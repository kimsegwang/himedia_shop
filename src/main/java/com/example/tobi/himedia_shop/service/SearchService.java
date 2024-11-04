package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.mapper.SearchProductMapper;
import com.example.tobi.himedia_shop.model.Products;
import com.example.tobi.himedia_shop.model.SearchProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchProductMapper searchProductMapper;
    private final ImageBase64Converter imageBase64Converter;

    public List<SearchProduct> searchProduct(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList(); // 빈 리스트 반환
        }
        List<Products> products = searchProductMapper.searchProducts(keyword);
        return convertToSearchProducts(products);
    }

    public List<SearchProduct> categoryProduct(String category, String subCategory) {
        if (isNullOrEmpty(category) || isNullOrEmpty(subCategory)) {
            return Collections.emptyList(); // 빈 리스트 반환
        }
        List<Products> products = searchProductMapper.categoryProduct(category, subCategory);
        return convertToSearchProducts(products);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private SearchProduct convertToSearchProduct(Products product)  {
        return SearchProduct.builder()
                .id(product.getId())
                .category(product.getCategory())
                .subCategory(product.getSubCategory())
                .sellerId(product.getSellerId())
                .title(product.getTitle())
                .content(product.getContent())
                .contentImg(imageBase64Converter.convertImageToBase64(product.getContentImg()))
                .created(parseDateTime(product.getCreated()))
                .updated(parseDateTime(product.getUpdated()))
                .stock(product.getStock()) // 수정된 부분
                .price(product.getPrice())
                .temperature(product.getTemperature())
                .precipitation(product.getPrecipitation())
                .build();
    }

    private List<SearchProduct> convertToSearchProducts(List<Products> products) {
        return products.stream()
                .map(this::convertToSearchProduct)
                .collect(Collectors.toList());
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr);
        } catch (Exception e) {
            // 예외 처리 로직 추가 (예: 로깅)
            return null; // 또는 기본값 반환
        }
    }
}
