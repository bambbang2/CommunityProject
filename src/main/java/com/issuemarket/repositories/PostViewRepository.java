package com.issuemarket.repositories;

import com.issuemarket.entities.PostView;
import com.issuemarket.entities.PostViewId;
import com.issuemarket.entities.QPostView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostViewRepository extends JpaRepository<PostView, PostViewId>, QuerydslPredicateExecutor<PostView> {

    // 게시글별 조회수
    default long getHit(Long id){
        QPostView postView = QPostView.postView;
        return count(postView.id.eq(id));
    }
}
