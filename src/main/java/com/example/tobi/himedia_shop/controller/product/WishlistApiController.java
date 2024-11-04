package com.example.tobi.himedia_shop.controller.product;

import com.example.tobi.himedia_shop.dto.product.wishlist.WishlistRequestDTO;
import com.example.tobi.himedia_shop.service.product.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/detail/api")
public class WishlistApiController {

    private final  WishlistService wishlistService;
    @PostMapping("/wishlist")
    public ResponseEntity<?> wishlist(@RequestBody WishlistRequestDTO  wishlistRequestDTO) {
        wishlistService.toggleWishlist(wishlistRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/wishlistCheck")
    public ResponseEntity<?> checkUserFavorites(@RequestParam String userId, @RequestParam String productId) {
        // 찜 목록 확인 로직
        boolean isFavorite = wishlistService.checkFavorites(userId, productId);
        if (isFavorite) {
            return ResponseEntity.ok().build(); // 찜 목록에 있을 경우 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 찜 목록에 없을 경우 404 Not Found
        }
    }
}
