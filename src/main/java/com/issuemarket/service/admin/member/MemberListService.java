package com.issuemarket.service.admin.member;

import com.issuemarket.dto.MemberInfo;
import com.issuemarket.dto.MemberSearch;
import com.issuemarket.entities.Member;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminMemberListService")
@RequiredArgsConstructor
public class MemberListService {

    private final MemberRepository repository;

    public List<MemberInfo> gets(MemberSearch search) {

        Page<Member> data = repository.getMembersByAdmin(search);
        List<Member> members = data.getContent();
        List<MemberInfo> memberInfos = members.stream().map(this::toConvert).toList();

        return memberInfos;
    }

    public MemberInfo get(Long userNo) {
        Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        MemberInfo memberInfo = MemberInfo.builder()
                .userNo(member.getUserNo())
                .userId(member.getUserId())
                .userNm(member.getUserNm())
                .userNick(member.getUserNick())
                .mobile(member.getMobile())
                .roles(member.getRoles())
                .createdAt(member.getCreatedAt())
                .build();

        return memberInfo;
    }

    private MemberInfo toConvert(Member member) {

        return MemberInfo.builder()
                .userNo(member.getUserNo())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .userNm(member.getUserNm())
                .userNick(member.getUserNick())
                .mobile(member.getMobile())
                .roles(member.getRoles())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }
}
