package com.issuemarket.entities;

import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Entity @Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_post_category", columnList = "category DESC"),
        @Index(name = "idx_post_createAt", columnList = "createdAt DESC")
})
public class Post extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bId")
    private Board board;

    @Column(length = 60, nullable = false)
    private String gid = UUID.randomUUID().toString();
    @Column(length = 30, nullable = false)
    private String poster;
    @Column(length = 60)
    private String guestPw;
    @Column(length = 30)
    private String category;
    @Column(nullable = false)
    private String subject;
    @Lob
    @Column(nullable = false)
    private String content;

    private int hit;
    @Column(length = 150)
    private String ua; // User-Agent (브라우저 정보)
    @Column(length = 20)
    private String ip;

    private int commentCnt; // 댓글수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member;
}
