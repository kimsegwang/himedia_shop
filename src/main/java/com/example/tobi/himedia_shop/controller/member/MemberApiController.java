package com.example.tobi.himedia_shop.controller.member;

import com.example.tobi.himedia_shop.dto.member.MemberRequestDTO;
import com.example.tobi.himedia_shop.dto.member.SignUpResponseDTO;
import com.example.tobi.himedia_shop.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService service;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public ResponseEntity<SignUpResponseDTO>  signUp(@RequestBody MemberRequestDTO requestDTO) {
        if(service.signUp(requestDTO.toMember(bCryptPasswordEncoder))){
            return ResponseEntity.ok(
                    SignUpResponseDTO.builder()
                            .url("/member/login")
                            .message("회원가입 성공")
                            .build()
            );

        }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    SignUpResponseDTO.builder()
                    .url("member/join")
                    .message("아이디 중복")
                    .build());
    }
}
