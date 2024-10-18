package com.example.tobi.himedia_shop.service.member;

import com.example.tobi.himedia_shop.mapper.MemberMapper;
import com.example.tobi.himedia_shop.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private  final MemberMapper memberMapper;

    public boolean signUp(Member member) {
        if(memberMapper.memberCheckId(member.getUserId())>0){
            return false;
        }
        memberMapper.memberInsert(member);
        Member byUserId = memberMapper.findByUserId(member.getUserId());
        memberMapper.memberRoleInsert(byUserId.getId());
        return true;
    }

}
