package com.issuemarket.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity @Data
@IdClass(PostView.class)
public class PostView {

    @Id
    private Long id; // 게시글 번호
    @Id
    @Column(length = 50, name = "uid_")
    private String uid; // unique Id (IP + User Agent + 회원번호)
}
