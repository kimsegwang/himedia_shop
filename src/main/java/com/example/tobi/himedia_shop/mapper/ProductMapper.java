package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.model.Products;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    Products getProductById(Integer id);
    List<Products> getProductALL();

}
