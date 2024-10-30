package com.example.tobi.himedia_shop.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponseDTO {
    private boolean isLoggedIn;
    private String url;
    private String userId;
    private String message;
}
