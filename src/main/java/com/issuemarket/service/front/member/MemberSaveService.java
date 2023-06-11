package com.issuemarket.service.front.member;

import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.social.ProfileResult;
import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

    public void save(MemberJoin memberJoin) {
        Member member = new ModelMapper().map(memberJoin,Member.class);

        ProfileResult profileResult = (ProfileResult) session.getAttribute("kakao");

        if(profileResult == null) {
            member.setUserPw(passwordEncoder.encode(memberJoin.getUserPw()));
        } else {
            String socialId = "" + profileResult.getId();
            String socialChannel = "kakao";
            member.setSocialId(socialId);
            member.setSocialChannel(socialChannel);
        }

        String nick = memberJoin.getUserNick();
        if (nick == null || nick.isEmpty()) {
            member.setUserNick(memberJoin.getUserNm());
        }

        repository.saveAndFlush(member);
    }
}