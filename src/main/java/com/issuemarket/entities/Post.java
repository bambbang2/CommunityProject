package com.issuemarket.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bId")
    private Board board;

    private String gid = UUID.randomUUID().toString();

    private String poster;

    private String guestPw;

    private String category;

    private String subject;

    private String content;

    private int hit;

    private String ua; // User-Agent (브라우저 정보)

    private String ip;

    private int commentCnt; // 댓글수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userNo")
    @ToString.Exclude
    private Member member;
}
