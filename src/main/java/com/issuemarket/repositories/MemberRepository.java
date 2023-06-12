package com.issuemarket.repositories;

import com.issuemarket.entities.Member;
import com.issuemarket.entities.QMember;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    Member findByUserId(String userId);

    Member findBySocialChannelAndSocialId(String channel, String id);

    Member findByUserNmAndMobile(String userNm, String mobile);

    default boolean exist(String userId) {
        QMember member = QMember.member;

        boolean result = exists(member.userId.eq(userId));

        return result;
    }

    default boolean exists(String socialChannel, String socialId) {
        QMember member = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.socialChannel.eq(socialChannel))
                .and(member.socialId.eq(socialId));

        return exists(builder);
    }
}