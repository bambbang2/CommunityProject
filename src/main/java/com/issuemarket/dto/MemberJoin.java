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

    private Long userNo;

    @NotBlank
    @Size(min = 5, max = 20)
    private String userId;

    @Size(min = 8)
    private String userPw;

    private String userPwRe;

    @NotBlank
    private String userNm;

    private String userNick;

    @NotBlank
    private String mobile;

    private String roles = "USER";

    /** 주소 관련 */
    private String zipcode;

    private String address;

    private String addressSub;

//    @AssertTrue
    private boolean[] agrees;
}
