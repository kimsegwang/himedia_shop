package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.product.ProductDetailResponseDTO;
import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;
import com.example.tobi.himedia_shop.mapper.product.ProductMapper;
import com.example.tobi.himedia_shop.model.Products;
import com.example.tobi.himedia_shop.service.ImageBase64Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ImageBase64Converter imageBase64Converter;

    @Transactional(readOnly = true)
    public ProductDetailResponseDTO getProductById(int productId) {
        Products productById = productMapper.getProductById(productId);
        imageBase64Converter.processImage(productById);  // 수정된 호출 방식

        return buildProductDetailResponse(productById);
    }

    @Transactional(readOnly = true)
    public List<ProductListResponseDTO> getAllProducts() {
        List<Products> productALL = productMapper.getProductALL();
        productALL.forEach(imageBase64Converter::processImage); // 수정된 호출 방식
        Collections.shuffle(productALL);
        return productALL.stream()
                .limit(3)
                .map(this::buildProductListResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductListResponseDTO> getProductRecommend(int productId) {
        List<Products> productALL = productMapper.getProductRecommend(productId);
        productALL.forEach(imageBase64Converter::processImage); // 수정된 호출 방식
        Collections.shuffle(productALL);
        return productALL.stream()
                .limit(5)
                .map(this::buildProductListResponse)
                .collect(Collectors.toList());
    }

    private ProductDetailResponseDTO buildProductDetailResponse(Products product) {
        return ProductDetailResponseDTO.builder()
                .id(product.getId())
                .category(product.getCategory())
                .title(product.getTitle())
                .content(product.getContent())
                .sellerId(product.getSellerId())
                .created(product.getCreated())
                .price(product.getPrice())
                .img(product.getContentImg()) // Base64 데이터가 설정된 이미지
                .stock(product.getStock())
                .build();
    }

    private ProductListResponseDTO buildProductListResponse(Products product) {
        return ProductListResponseDTO.builder()
                .id(product.getId())
                .category(product.getCategory())
                .title(product.getTitle())
                .content(product.getContent())
                .sellerId(product.getSellerId())
                .created(product.getCreated())
                .price(product.getPrice())
                .contentImg(product.getContentImg())
                .stock(product.getStock())
                .build();
    }
}
