package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.history.PurchaseHistoryWithProductDTO;
import com.example.tobi.himedia_shop.model.Member;
import com.example.tobi.himedia_shop.model.Products;
import com.example.tobi.himedia_shop.model.PurchaseHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {
    List<PurchaseHistoryWithProductDTO> findPurchaseHistoryWithProductByUserId(@Param("userId") String userId);
}
