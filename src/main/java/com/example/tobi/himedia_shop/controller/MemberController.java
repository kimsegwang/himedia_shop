package com.example.tobi.himedia_shop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
public class MemberController {

    @GetMapping("/join")
    public String signUp() {
        return "sign-up";
    }
    @GetMapping("/login")
    public String signIn() {
        return "sign-in";
    }
}
