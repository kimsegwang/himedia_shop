package com.example.tobi.himedia_shop.dto.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpResponseDTO {
    private String url;
    private String message;
}
