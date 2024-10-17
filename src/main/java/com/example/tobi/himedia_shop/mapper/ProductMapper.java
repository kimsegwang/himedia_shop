package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.ProductListResponseDTO;
import com.example.tobi.himedia_shop.model.Products;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    Products getProductById(String id);
    List<Products> getProductALL();
}
