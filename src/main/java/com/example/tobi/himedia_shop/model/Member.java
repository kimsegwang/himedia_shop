package com.example.tobi.himedia_shop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Member {
    private String userId;
    private String password;
    private String myLocation;
    private String phone;
}
