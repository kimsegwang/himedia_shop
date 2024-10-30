package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.model.Member;
import com.example.tobi.himedia_shop.model.Products;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    void saveProduct(Products build);
    List<Member> getMember();
}
