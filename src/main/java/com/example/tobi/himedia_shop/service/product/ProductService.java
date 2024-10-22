package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.dto.ProductDetailResponseDTO;
import com.example.tobi.himedia_shop.dto.ProductListResponseDTO;
import com.example.tobi.himedia_shop.mapper.ProductMapper;
import com.example.tobi.himedia_shop.model.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    public ProductDetailResponseDTO getProductById(String productId) {
        Products productById = productMapper.getProductById(productId);
        return  ProductDetailResponseDTO.builder()
                .category(productById.getCategory())
                .title(productById.getTitle())
                .content(productById.getContent())
                .sellerId(productById.getSellerId())
                .created(productById.getCreated())
                .price(productById.getPrice())
                .img(productById.getContentImg())
                .stock(productById.getStock())
                .build();
    }
    public List<ProductListResponseDTO> getAllProducts() {
        List<Products> productALL = productMapper.getProductALL();
        return productALL.stream().map(products -> ProductListResponseDTO.builder()
                        .id(products.getId())
                        .category(products.getCategory())
                        .title(products.getTitle())
                        .content(products.getContent())
                        .sellerId(products.getSellerId())
                        .created(products.getCreated())
                        .price(products.getPrice())
                        .contentImg(products.getContentImg())
                        .stock(products.getStock())
                        .build())
                .collect(Collectors.toList());
    }

}
