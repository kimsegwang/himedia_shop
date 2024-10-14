package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.mapper.MemberMapper;
import com.example.tobi.himedia_shop.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private  final MemberMapper memberMapper;


    public void signUp(Member member) {
        memberMapper.memberInsert(member);
    }

}
