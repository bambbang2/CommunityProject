package com.issuemarket.service.admin.member;

import com.issuemarket.entities.Member;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberDeleteService {

    private final MemberRepository repository;

    public void delete(Long userNo) {
        delete(userNo, false);
    }

    public void delete(Long userNo, boolean isComplete) {
        Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        if (isComplete) {
            repository.delete(member);
        } else {
            member.setDeletedAt(LocalDateTime.now());
        }
        repository.flush();
    }
}
