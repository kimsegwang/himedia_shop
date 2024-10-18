package com.example.tobi.himedia_shop.config.security;

import com.example.tobi.himedia_shop.dto.member.SignInResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");

        String referer = request.getParameter("referer");
        String redirectUrl = (referer != null && !referer.isEmpty()) ? referer : "/"; // referer가 있으면 사용, 없으면 기본 페이지로
        SignInResponseDTO build= SignInResponseDTO.builder()
                .isLoggedIn(false)
                .message("로그인 실패\n다시 로그인해주세요")
                .url(redirectUrl) // 리디렉션 URL
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(build));

    }
}
