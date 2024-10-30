package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.member.MemberUpdateRqDTO;
import com.example.tobi.himedia_shop.dto.permission.Role;
import com.example.tobi.himedia_shop.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    void memberInsert(Member user);
    void memberRoleInsert(int userId);
    Member findByUserId(String userId);
    List<Role> findRolesByUserId(int userId);
    int memberCheckId(String userId);

    Member getUserById(String userId);


    int memberCheckingId(String userId);
    // 회원정보 업데이트
    void updateMember(Member member);



}
