package com.example.tobi.himedia_shop.service.admin;

import com.example.tobi.himedia_shop.dto.product.product.ProductRequestDTO;
import com.example.tobi.himedia_shop.mapper.AdminMapper;
import com.example.tobi.himedia_shop.model.Member;
import com.example.tobi.himedia_shop.model.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final FileService fileService;
    private final AdminMapper adminMapper;

    public void productRegistration(ProductRequestDTO prDTO) {
        String path = null;
        if(!prDTO.getProductImage().isEmpty()){
            path=fileService.fileUpload(prDTO.getProductImage(),"product/");
        }

        adminMapper.saveProduct(Products.builder()
                .title(prDTO.getProductName())
                .sellerId(prDTO.getProductUserId())
                .category(prDTO.getProductCategory())
                .content(prDTO.getProductContent())
                .contentImg(path)
                .stock(prDTO.getProductStock())
                .price(prDTO.getProductPrice())
                .temperature(prDTO.getProductTemperature())
                .precipitation(prDTO.getProductPrecipitation())
                .build()
        );
    }

    public List<Member> getMembers() {
        return adminMapper.getMember();
    }
}
