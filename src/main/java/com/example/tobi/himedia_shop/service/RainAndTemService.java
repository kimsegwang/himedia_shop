package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;

import com.example.tobi.himedia_shop.dto.RainAndTemResponseDTO;
import com.example.tobi.himedia_shop.mapper.ProductMapper;
import com.example.tobi.himedia_shop.model.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RainAndTemService {
    private final ProductMapper productMapper;


    public List<ProductListResponseDTO> Divide(int tem, int rain) {
        RainAndTemResponseDTO build = RainAndTemResponseDTO.builder().temperature(tem).precipitation(rain).build();
        List<Products> productWeather = productMapper.getProductWeather(build);
        return productWeather.stream()
                        .map(products -> ProductListResponseDTO.builder()
                                .title(products.getTitle())
                                .price(products.getPrice())
                                .contentImg(products.getContentImg())
                                .build())
                .collect(Collectors.toList());
    }
}
