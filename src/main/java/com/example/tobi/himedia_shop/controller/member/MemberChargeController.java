package com.example.tobi.himedia_shop.controller.member;

import com.example.tobi.himedia_shop.service.member.MemberChargeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberChargeController {
    private final MemberChargeService memberChargeService;

    @PostMapping("/deposit")
    @ResponseBody
    public Map<String, Object> deposit(HttpSession session, @RequestBody Map<String, Integer> requestData) {
        int amount = requestData.get("amount");
        int balance = requestData.get("balance");

        System.out.println(balance);
        System.out.println(amount);

        // 예시: 사용자 잔액 업데이트 처리
        boolean success = memberChargeService.updateUserBalance(session, amount, String.valueOf(balance));

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        return response;
    }

    @PostMapping("/Withdrawal")
    @ResponseBody
    public Map<String, Object> Withdrawal(HttpSession session, @RequestBody Map<String, Integer> requestData) {
        int amount = requestData.get("amount");
        int balance = requestData.get("balance");

        System.out.println(balance);
        System.out.println(amount);

        // 예시: 사용자 잔액 업데이트 처리
        boolean success = memberChargeService.WithdrawalUserBalance(session, amount, String.valueOf(balance));

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        return response;
    }
}
