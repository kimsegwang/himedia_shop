package com.example.tobi.himedia_shop.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Member {
    private int id;
    private String userId;
    private String password;
    private String myLocation;
    private String phone;
    private String name;

}
