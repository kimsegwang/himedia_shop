package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.dto.MemberRequestDTO;
import com.example.tobi.himedia_shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService service;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @PostMapping("/join")
    public String signUp(@RequestBody MemberRequestDTO requestDTO) {
        service.signUp(requestDTO.toMember(bCryptPasswordEncoder));
        return "sign-up";
    }
    @PostMapping("/login")
    public String signIn() {
        return "sign-in";
    }
}
