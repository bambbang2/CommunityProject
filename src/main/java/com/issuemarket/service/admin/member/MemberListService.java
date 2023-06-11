package com.issuemarket.service.admin.member;

import com.issuemarket.dto.MemberSearch;
import com.issuemarket.entities.Member;
import com.issuemarket.entities.QMember;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("adminMemberListService")
@RequiredArgsConstructor
public class MemberListService {

    private final MemberRepository repository;

    public Page<Member> gets(MemberSearch search){
        QMember member = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();

        int page = search.getPage();
        int pageSize = search.getNoOfRows();

        page = page < 1 ? 1 : page;
        pageSize = pageSize < 1 ? 20 : pageSize;

        String sopt = search.getSopt();
        String skey = search.getSkey();

        if (search != null) {
            LocalDate sDate = search.getSDate();
            LocalDate eDate = search.getEDate();

            if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
                sopt = sopt.trim();
                skey = skey.trim();

                if (sopt.equals("all")) {
                    builder.and(member.userId.contains(skey))
                            .or(member.userNm.contains(skey))
                            .or(member.userNick.contains(skey))
                            .or(member.mobile.contains(skey));
                } else if (sopt.equals("userId")) {
                    builder.and(member.userId.contains(skey));
                } else if (sopt.equals("userNm")) {
                    builder.and(member.userNm.contains(skey));
                } else if (sopt.equals("userNick")) {
                    builder.and(member.userNick.contains(skey));
                } else if (sopt.equals("mobile")) {
                    builder.and(member.mobile.contains(skey));
                }
            }

            if (sDate != null) {
                builder.and(member.createdAt.goe(sDate.atStartOfDay()));
            }

            if (eDate != null) {
                eDate = eDate.plusDays(1);
                builder.and(member.createdAt.lt(eDate.atStartOfDay()));
            }
        }

        Pageable pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.asc("createdAt")));
        Page<Member> members = repository.findAll(builder, pageable);

        return members;
    }

    public Member get(Long userNo) {
        Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        return member;
    }
}