package com.issuemarket.service.front.member;

import com.issuemarket.dto.MemberInfoRequest;
import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.repositories.MyPageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    // 회원 정보 얻기
    public Optional<Member> getMember(String userId){

        Optional<Member> member = myPageRepository.findByUserId(userId);

       return member;
    }

    // 회원 정보 수정
    public void updateMember(MemberInfoRequest memberInfoRequest, Principal principal){

        // 다른 사람이 수정했는지 유효성 검사
        System.out.println("------------------");
        System.out.println(principal.getName());
        System.out.println("------------------");

        String userName = principal.getName();
        String formUserName = memberInfoRequest.getUserId();
        String userId = userName;

        Member member = memberRepository.findByUserId(userId);

        if(!userName.equals(formUserName)){
            throw new IllegalArgumentException();
        }

        // 비밀번호 검사
        String formPassword = memberInfoRequest.getUserPw();
        String password = member.getUserPw(); // 암호화됨

        String encodedPassword = passwordEncoder.encode(formPassword); // 폼에서 넘어온 값 인코딩

        if(!passwordEncoder.matches(formPassword, password)){
            throw new IllegalArgumentException();
        }

        // 비밀번호 같은지 확인
        String newPassword = memberInfoRequest.getUserNewPw();
        String newPasswordRe = memberInfoRequest.getUserNewPwRe();

        if(!newPasswordRe.equals(newPasswordRe)){
            throw new IllegalStateException();
        }

        // 회원정보 업데이트
        member.updateMember(memberInfoRequest, passwordEncoder);

    }
}








