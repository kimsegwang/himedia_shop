package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.history.PurchaseHistoryWithProductDTO;
import com.example.tobi.himedia_shop.dto.shoppingcart.ShoppingCartDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCartDTO> findShoppingCartWithProductByUserId(@Param("userId") String userId,            @Param("offset") int offset,
                                                              @Param("pageSize") int pageSize);

    int findPurchaseLike(@Param("userId") String userId);
}
