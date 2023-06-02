package com.issuemarket.entities;

import com.issuemarket.commons.constants.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {

    private String mode; // update - 수정, null - 추가

    @NotBlank
    private String bId; // 게시판 Id

    @NotBlank
    private String bName; // 게시판명

    private boolean use; // 사용여부

    private int rowsOfPage = 20; // 페이지당 글 갯수

    private boolean showViewList; // 게시글 하단 목록

    private String category; // 카테고리 분류

    /** 접근 권한 */
    private String listAccessRole = "ALL"; // 목록

    private String viewAccessRole = "ALL"; // 글보기

    private String writeAccessRole = "ALL"; // 글쓰기

    private String replyAccessRole = "ALL"; // 답글

    private String commentAccessRole = "ALL"; // 댓글

    /** 기능 */
    private boolean useEditor; // 에디터

    private boolean useAttachFile; // 파일 첨부

    private boolean useAttachImage; // 이미지 첨부

    private String locationAfterWriting = "view"; // 글 작성후 게시글 or 목록 이동

    private boolean useReply; // 답급

    private boolean useComment; // 댓글

}
