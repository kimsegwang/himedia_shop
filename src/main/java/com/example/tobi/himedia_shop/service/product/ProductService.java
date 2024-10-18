package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.product.ProductDetailResponseDTO;
import com.example.tobi.himedia_shop.mapper.ProductMapper;
import com.example.tobi.himedia_shop.model.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    public ProductDetailResponseDTO getProductById(Integer productId) {
        Products productById = productMapper.getProductById(productId);
        return  ProductDetailResponseDTO.builder()
                .id(productById.getId())
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

}
