package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void memberInsert(Member user);
    Member signIn(String userId);
}
