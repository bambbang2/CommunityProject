package com.issuemarket.service.admin.member;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.commons.constants.Role;
import com.issuemarket.dto.MemberJoin;
import com.issuemarket.entities.Member;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;

    public void update(Long userNo, MemberJoin memberJoin) {

        if (memberUtil.isAdmin()) {
            Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);

            String nick = memberJoin.getUserNick();
            String role = memberJoin.getRoles();

            if (nick == null || nick.isBlank()) {
                member.setUserNick(member.getUserNick());
            } else {
                member.setUserNick(nick);
            }

            member.setRoles(Role.valueOf(role));

            repository.saveAndFlush(member);
        }
    }

    public void pwUpdate(Long no, String pw) {
        Member member = repository.findById(no).orElseThrow(MemberNotFoundException::new);

        member.setUserPw(passwordEncoder.encode(pw));

        repository.saveAndFlush(member);
    }
}
