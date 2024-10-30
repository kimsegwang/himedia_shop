package com.example.tobi.himedia_shop.service.member;

import com.example.tobi.himedia_shop.dto.member.MemberUpdateRqDTO;
import com.example.tobi.himedia_shop.mapper.MemberMapper;
import com.example.tobi.himedia_shop.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberUpdateService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 아이디 중복 체크
    public boolean isUserIdExists(String userId) {
        return memberMapper.memberCheckingId(userId) > 0;
    }

    // 비밀번호 확인
    public boolean checkPassword(String userId, String password) {
        Member member = memberMapper.getUserById(userId);
        return passwordEncoder.matches(password, member.getPassword());
    }

    // 회원정보 업데이트
    public void updateMemberInfo(Member member) {

        memberMapper.updateMember(member);
    }

}
