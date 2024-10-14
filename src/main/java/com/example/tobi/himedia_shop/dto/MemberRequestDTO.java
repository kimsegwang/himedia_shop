package com.example.tobi.himedia_shop.dto;

import com.example.tobi.himedia_shop.model.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MemberRequestDTO {
    private String userId;
    private String password;
    private String my_location;
    private String phone;

    public Member toMember(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .userId(userId)
                .password(bCryptPasswordEncoder.encode(password))
                .my_location(my_location)
                .build();
    }
}
