package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.model.SearchProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchProductMapper {
    List<SearchProduct> searchProducts(String keyword);
}
