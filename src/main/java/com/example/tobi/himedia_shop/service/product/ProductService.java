package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.buy.BuyProductRequestDTO;
import com.example.tobi.himedia_shop.dto.product.product.ProductDetailResponseDTO;
import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;
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
    public ProductDetailResponseDTO getProductById(int productId) {
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

    public void buyProduct(BuyProductRequestDTO buyProductRequestDTO) {
        int i = productMapper.buyProduct(
                Products.builder()
                        .id(buyProductRequestDTO.getProductId())
                        .price(buyProductRequestDTO.getPrice())
                        .stock(buyProductRequestDTO.getStock())
                        .sellerId(buyProductRequestDTO.getUserId())
                        .build()
        );
        if(i>0){
            //int i1 = productMapper.getProductById(buyProductRequestDTO.getProductId()) - buyProductRequestDTO.getStock();
           // productMapper.updateProduct(i1);
        }

    }
}
