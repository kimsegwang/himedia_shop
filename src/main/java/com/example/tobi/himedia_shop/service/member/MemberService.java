package com.example.tobi.himedia_shop.service.member;

import com.example.tobi.himedia_shop.mapper.member.MemberMapper;
import com.example.tobi.himedia_shop.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private  final MemberMapper memberMapper;
    @Transactional
    public boolean signUp(Member member) {
        if(memberMapper.memberCheckId(member.getUserId())>0){
            return false;
        }
        memberMapper.memberInsert(member);
        Member byUserId = memberMapper.findByUserId(member.getUserId());
        memberMapper.memberRoleInsert(byUserId.getId());
        return true;
    }

    public Member getUserById(String id) {
        return memberMapper.getUserById(id);
    }





}
