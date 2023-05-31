package com.issuemarket.service;

import com.issuemarket.dto.MemberInfo;
import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByUserId(username); // 회원이 존재하는지 확인, 없으면 이미 설정되있는 예외 발생
        if(member == null){
          throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getRoles().toString()));

        return MemberInfo.builder() // session에서 사용자 정보를 불러 올 수 있게 memberInfo 회원정보 빌더패턴으로 추가
                .userNo(member.getUserNo())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .userNm(member.getUserNm())
                .userNick(member.getUserNick())
                .mobile(member.getMobile())
                .roles(member.getRoles())
                .authorities(authorities)
                .build();

    }


}