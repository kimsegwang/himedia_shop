package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.model.Products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchProductMapper {
    List<Products> searchProducts(String keyword);
    List<Products> categoryProduct(@Param("category") String category, @Param("subCategory") String subCategory);


}

