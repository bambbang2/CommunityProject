package com.issuemarket.repositories;

import com.issuemarket.dto.MemberSearch;
import com.issuemarket.entities.Member;
import com.issuemarket.entities.QMember;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    Member findByUserId(String userId);

    default boolean exist(String userId) {
        QMember member = QMember.member;
        boolean result = exists(member.userId.eq(userId));

        return false;
    }

    default Page<Member> getMembersByAdmin(MemberSearch search) {
        BooleanBuilder builder = new BooleanBuilder();
        QMember member = QMember.member;


        int page = search.getPage();
        int noOfRows = search.getNoOfRows();

        if (search != null) {
            String userId = search.getUserId();
            String userNm = search.getUserNm();
            String userNick = search.getUserNick();
            LocalDate sDate = search.getSDate();
            LocalDate eDate = search.getEDate();

            if (userId != null && !userId.isBlank()) {
                builder.and(member.userId.contains(userId));
            }

            if (userNm != null && !userNm.isBlank()) {
                builder.and(member.userNm.contains(userNm));
            }

            if (userNick != null && !userNick.isBlank()) {
                builder.and(member.userNick.contains(userNick));
            }

            if (sDate != null) {
                builder.and(member.createdAt.goe(sDate.atStartOfDay()));
            }

            if (eDate != null) {
                eDate = eDate.plusDays(1);
                builder.and(member.createdAt.lt(eDate.atStartOfDay()));
            }
        }

        Pageable pageable = PageRequest.of(page -1, noOfRows, Sort.by(desc("createdAt")));
        Page<Member> data = findAll(builder, pageable);

        return data;
    }
}