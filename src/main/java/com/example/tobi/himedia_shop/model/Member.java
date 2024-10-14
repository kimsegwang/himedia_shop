package com.example.tobi.himedia_shop.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {
    private String userId;
    private String my_location;
    private String phone;
    private String password;
}
