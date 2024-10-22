package com.example.tobi.himedia_shop.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/join")
    public String signUp() {
        return "member/sign-up";
    }
    @GetMapping("/login")
    public String signIn() {
        return "member/sign-in";
    }
}
