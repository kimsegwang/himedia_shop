package com.example.tobi.himedia_shop.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberAdminResponseDTO {
    private int id;
    private String username;
    private String password;
    private String phone;
    private String myLocation;
}
