package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.model.Products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchProductMapper {
    List<Products> searchProducts(@Param("keyword")String keyword,@Param("limit")int limit,@Param("offset")int offset);
    List<Products> categoryProduct(@Param("category") String category, @Param("subCategory") String subCategory);
    List<Products> searchProductsSortBy(@Param("searchKeyword")String searchKeyword,@Param("sortBy") String sortBy);
}

