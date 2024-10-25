package com.example.tobi.himedia_shop.controller.member;

import com.example.tobi.himedia_shop.dto.member.MemberUpdateRqDTO;
import com.example.tobi.himedia_shop.model.Member;
import com.example.tobi.himedia_shop.service.member.MemberService;
import com.example.tobi.himedia_shop.service.member.MemberUpdateService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberUpdateController {

    private final MemberService memberService;
    private final MemberUpdateService memberUpdateService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/myinfo")
    public String myInfo(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        Member member = memberService.getUserById(userId);
        model.addAttribute("member", member);
        return "member/myinfo";
    }
    @GetMapping("/update")
    public String updateGet(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        Member member = memberService.getUserById(userId);
        model.addAttribute("member", member);
        return "member/update";
    }
    // 회원정보 수정 처리
    @PostMapping("/update")
    public String updateMemberInfo(@ModelAttribute MemberUpdateRqDTO member, HttpSession session, Model model) {
        String currentUserId = (String) session.getAttribute("userId");
        int currentId = (int) session.getAttribute("id");
        // 비밀번호 재확인
        boolean passwordCorrect = memberUpdateService.checkPassword(currentUserId, member.getCurrentPassword());
        if (!passwordCorrect) {
            model.addAttribute("errorMessage", "Current password is incorrect.");
            return "/";  // 비밀번호가 틀리면 다시 회원정보 페이지로 이동
        }

        // 회원정보 업데이트
        memberUpdateService.updateMemberInfo(member.updateMember(bCryptPasswordEncoder,currentId));

            session.setAttribute("userId", member.getUserId());
        // 업데이트 성공 후 리다이렉트
        return "redirect:/myinfo";
    }

    // 아이디 중복 체크 (AJAX 요청 처리)
    @PostMapping("/checkUserId")
    @ResponseBody
    public boolean checkUserId(@RequestParam String userId) {
        return !memberUpdateService.isUserIdExists(userId);  // 아이디 중복 여부 반환
    }
}
