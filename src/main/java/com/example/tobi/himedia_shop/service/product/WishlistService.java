package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.wishlist.WishlistRequestDTO;
import com.example.tobi.himedia_shop.mapper.product.WishlistMapper;
import com.example.tobi.himedia_shop.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistMapper wishlistMapper;

    // 찜 목록에 상품을 추가하거나 제거합니다.
    public void toggleWishlist(WishlistRequestDTO wishlistRequestDTO) {
        Wishlist wishlist = Wishlist.builder()
                .userId(wishlistRequestDTO.getUserId())
                .productId(wishlistRequestDTO.getProductId())
                .build(); // Wishlist 모델 생성

        // 찜 상태가 true이면 추가, false이면 제거
        if (wishlistRequestDTO.isFavorite()) {
            addFavoriteIfNotExists(wishlist);
        } else {
            removeFavoriteIfExists(wishlist);
        }
    }

    // 찜 목록에 없으면 추가합니다.
    private void addFavoriteIfNotExists(Wishlist wishlist) {
        if (wishlistMapper.getFavorites(wishlist) <= 0) {
            wishlistMapper.addFavorites(wishlist);
        }
    }

    // 찜 목록에 있으면 제거합니다.
    private void removeFavoriteIfExists(Wishlist wishlist) {
        if (wishlistMapper.getFavorites(wishlist) > 0) {
            wishlistMapper.deleteFavorites(wishlist);
        }
    }

    // 찜 목록에 있는지 확인합니다.
    public boolean checkFavorites(String userId, String productId) {
        return wishlistMapper.getFavorites(Wishlist.builder()
                .productId(productId)
                .userId(userId)
                .build()) > 0;
    }
}
