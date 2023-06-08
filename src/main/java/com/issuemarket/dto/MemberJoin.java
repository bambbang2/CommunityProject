package com.issuemarket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Member DTO ( 회원가입 이용 )
 */
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberJoin {

    @NotBlank
    @Size(min = 5, max = 20)
    private String userId;

    @NotBlank
    @Size(min = 8)
    private String userPw;

    @NotBlank
    private String userPwRe;

    @NotBlank
    private String userNm;

    private String userNick;

    @NotBlank
    private String mobile;

//    @AssertTrue
    private boolean[] agrees;
}
