package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;
import com.example.tobi.himedia_shop.dto.product.product.ProductRequestDTO;
import com.example.tobi.himedia_shop.dto.RainAndTemResponseDTO;
import com.example.tobi.himedia_shop.model.Products;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    Products getProductById(int id);
    //List<Products> getProductALL();
    List<Products> getProductWeather(RainAndTemResponseDTO dto);
    List<Products> getProductALL();
}
