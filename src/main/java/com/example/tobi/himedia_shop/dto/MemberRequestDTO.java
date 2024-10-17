package com.example.tobi.himedia_shop.dto;

import com.example.tobi.himedia_shop.model.Member;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class MemberRequestDTO {
    private String userId;
    private String password;
    private String location;

    private String phone;

    public Member toMember(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .userId(userId)
                .password(bCryptPasswordEncoder.encode(password))
                .myLocation(location)
                .phone(phone)
                .build();
    }
}
