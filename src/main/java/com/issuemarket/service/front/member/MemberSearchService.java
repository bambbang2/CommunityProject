package com.issuemarket.service.front.member;

import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberRepository repository;

    public String idSearch(String userNm, String mobile) {

        Member member = repository.findByUserNmAndMobile(userNm, mobile);

        String userId = member.getUserId();

        return userId;
    }

    public Long pwSearch(String userId, String userNm, String mobile) {

        Member member = repository.findByUserIdAndUserNmAndMobile(userId, userNm, mobile);

        Long memberNo = member.getUserNo();

        return memberNo;
    }
}
