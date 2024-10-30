package com.example.tobi.himedia_shop.dto.member;

import com.example.tobi.himedia_shop.model.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class MemberUpdateRqDTO {
    private int id;
    private String userId;
    private String password;
    private String myLocation;
    private String phone;
    private String name;
    private String currentPassword;

    public Member updateMember(BCryptPasswordEncoder bCryptPasswordEncoder,int ids) {
        return Member.builder()
                .id(ids)
                .userId(userId)
                .password(bCryptPasswordEncoder.encode(password))
                .myLocation(myLocation)
                .phone(phone)
                .name(name)
                .build();
    }
}
