package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.mapper.SearchProductMapper;
import com.example.tobi.himedia_shop.model.SearchProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchProductMapper searchProductMapper;

    public List<SearchProduct> searchProduct(String keyword) {
        return searchProductMapper.searchProducts(keyword);

    }

}
