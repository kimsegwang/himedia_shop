package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.config.security.CustomUserDetails;
import com.example.tobi.himedia_shop.dto.permission.Role;
import com.example.tobi.himedia_shop.mapper.MemberMapper;
import com.example.tobi.himedia_shop.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberMapper.findByUserId(username);
        if(member == null) {
            throw new UsernameNotFoundException(username+"not found");
        }

        // 사용자의 역할을 조회
        List<Role> roles = memberMapper.findRolesByUserId(member.getId()); // 역할 조회
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return CustomUserDetails.builder()
                .member(member)
                .authorities(authorities) // 권한 추가
                .build();
    }
}
