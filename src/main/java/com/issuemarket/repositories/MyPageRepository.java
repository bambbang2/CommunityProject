package com.issuemarket.repositories;

import com.issuemarket.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MyPageRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {


    Optional<Member> findByUserId(String userId);
}
