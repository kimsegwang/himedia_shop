package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.model.Member;
import com.example.tobi.himedia_shop.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @GetMapping
    public String admin(Model model) {
        List<Member> members = adminService.getMembers();
        model.addAttribute("members", members);
        return "admin-page";
    }
}
