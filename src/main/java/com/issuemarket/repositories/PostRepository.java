package com.issuemarket.repositories;

import com.issuemarket.entities.Member;
import com.issuemarket.entities.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {

    List<Post> findByMember(Member member, Sort sort);
}
