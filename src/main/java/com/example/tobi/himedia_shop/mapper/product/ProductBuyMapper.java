package com.example.tobi.himedia_shop.mapper.product;

import com.example.tobi.himedia_shop.dto.product.buy.StockUpdateDTO;
import com.example.tobi.himedia_shop.model.PurchaseHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface  ProductBuyMapper {
    int buyProduct(PurchaseHistory productsBuilder);

    void updateProduct(StockUpdateDTO dto);

}
