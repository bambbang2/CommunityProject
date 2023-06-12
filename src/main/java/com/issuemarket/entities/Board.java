package com.issuemarket.entities;

import com.issuemarket.commons.constants.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity @Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseMemberEntity {

    @Id
    @Column(length = 50)
    private String bId; // 게시판 Id

    @Column(length = 100)
    private String bName; // 게시판명

    @Column(name="_use")
    private boolean use; // 사용여부

    private int rowsOfPage = 20; // 페이지당 글 갯수

    private boolean showViewList; // 게시글 하단 목록

    @Lob
    private String category; // 카테고리 분류

    /** 접근 권한 */
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role listAccessRole = Role.ALL; // 목록

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role viewAccessRole = Role.ALL; // 글보기

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role writeAccessRole = Role.ALL; // 글쓰기

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role replyAccessRole = Role.ALL; // 답글

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role commentAccessRole = Role.ALL; // 댓글

    /** 기능 */
    private boolean useEditor; // 에디터

    private boolean useAttachFile; // 파일 첨부

    private boolean useAttachImage; // 이미지 첨부

    @Column(length = 10, nullable = false)
    private String locationAfterWriting = "view"; // 글 작성후 게시글 or 목록 이동

    private boolean useReply; // 답급

    private boolean useComment; // 댓글

    @Transient
    private boolean isGuest;

    @OneToMany(mappedBy = "board")
    @ToString.Exclude
    private List<Post> postList = new ArrayList<>();

    // 게시판 카테고리
    public String[] getCategories() {
        if (category == null) {
            return null;
        }

        String[] categories = category.replaceAll("\\r", "").trim().split("\\n");

        return categories;
    }
}
