package com.example.tobi.himedia_shop.mapper.product;

import com.example.tobi.himedia_shop.model.Wishlist;

public interface WishlistMapper {

    int getFavorites(Wishlist wishlist);

    void addFavorites(Wishlist wishlist);

    void deleteFavorites(Wishlist wishlist);
}
