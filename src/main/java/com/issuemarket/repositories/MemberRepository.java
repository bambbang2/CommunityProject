package com.issuemarket.repositories;

import com.issuemarket.entities.Member;
import com.issuemarket.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    Member findByUserId(String userId);

    default boolean exist(String userId) {
        QMember member = QMember.member;
        boolean result = exists(member.userId.eq(userId));

        return false;
    }

}
