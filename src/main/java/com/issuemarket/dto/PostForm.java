package com.issuemarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostForm {

    private Long id;

    @NotBlank
    private String bId;

    @NotBlank
    private String gid = UUID.randomUUID().toString();

    @NotBlank
    private String poster;

    private String guestPw;

    @NotBlank
    private String category;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    private Long userNo; // 회원번호
}
