package com.example.tobi.himedia_shop.config.security;

import com.example.tobi.himedia_shop.dto.member.SignInResponseDTO;
import com.example.tobi.himedia_shop.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        HttpSession session = request.getSession();
        session.setAttribute("userId", member.getUserId());
        session.setAttribute("location", member.getMyLocation());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=utf-8" );

        String referer = request.getParameter("referer"); // referer 파라미터 가져오기
        String redirectUrl = (referer != null && !referer.isEmpty()) ? referer : "/"; // referer가 있으면 사용, 없으면 기본 페이지로
        SignInResponseDTO build = SignInResponseDTO.builder()
                .isLoggedIn(true)
                .message("로그인 성공")
                .url(redirectUrl) // 리디렉션 URL
                .userId(member.getUserId())
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(build)); // JSON 응답
    }
}
