package com.issuemarket.service.admin.member;

import com.issuemarket.entities.Member;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDeleteService {

    private final MemberRepository repository;

    public void delete(Long userNo) {
        Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        repository.delete(member);

        repository.flush();
    }
}
