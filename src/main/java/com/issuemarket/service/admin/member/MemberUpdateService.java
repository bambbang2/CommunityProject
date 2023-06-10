package com.issuemarket.service.admin.member;

import com.issuemarket.dto.MemberJoin;
import com.issuemarket.entities.Member;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository repository;

    public void update(Long userNo, MemberJoin memberJoin) {
        Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        String nick = memberJoin.getUserNick();

        if (nick == null || nick.isBlank()) {
            member.setUserNick(member.getUserNick());
        } else {
            member.setUserNick(nick);
        }
        repository.saveAndFlush(member);
    }
}
