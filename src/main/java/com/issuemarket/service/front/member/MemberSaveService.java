package com.issuemarket.service.front.member;

import com.issuemarket.dto.MemberJoin;
import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository repository;

    private final PasswordEncoder passwordEncoder;

    public void save(MemberJoin memberJoin) {
        Member member = new ModelMapper().map(memberJoin,Member.class);
        member.setUserPw(passwordEncoder.encode(memberJoin.getUserPw()));

        repository.saveAndFlush(member);
    }
}