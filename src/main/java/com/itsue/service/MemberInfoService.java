package com.itsue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

//    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = repository.findByUserId(username); // 회원이 존재하는지 확인, 없으면 이미 설정되있는 예외 발생
//        if(member == null){
//          throw new UsernameNotFoundException(username);
//        }

//        List<GrantedAuthority> authorities
//                = Arrays.asList(new SimpleGrantedAuthority(member.getRoles().toString()));

//        return MemberInfo.builder() // session에서 사용자 정보를 불러 올 수 있게 memberInfo 회원정보 빌더패턴으로 추가
//                .userNo(member.getUserNo())
//                .userId()
//                .userPw()
//                .userNm()
//                .email()
//                .mobile()
//                .roles(member.getRoles())
//                .authorities(authorities)
//                .build();

        return null;
    }
}