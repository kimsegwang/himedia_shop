package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.product.wishlist.WishlistRequestDTO;
import com.example.tobi.himedia_shop.model.Wishlist;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WishlistMapper {

    int getFavorites(Wishlist wishlist);

    void addFavorites(Wishlist wishlist);

    void deleteFavorites(Wishlist wishlist);
}
