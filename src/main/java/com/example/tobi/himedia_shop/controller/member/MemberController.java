package com.example.tobi.himedia_shop.controller.member;

import com.example.tobi.himedia_shop.model.Member;
import com.example.tobi.himedia_shop.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String signUp() {
        return "member/sign-up";
    }
    @GetMapping("/login")
    public String signIn() {
        return "member/sign-in";
    }

}
