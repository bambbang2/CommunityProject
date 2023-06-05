package com.issuemarket.dto;

import com.issuemarket.commons.constants.Role;
import com.issuemarket.entities.BaseEntity;
import com.issuemarket.entities.Member;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
public class MemberInfoRequest{

    private Long userNo;  // 회원 번호 ( 회원이 입력 X )

    private String userId;  // 아이디

    private String userPw;  // 비밀번호

    private String userNewPw; // 새 비밀번호

    private String userNewPwRe; // 새 비밀번호 확인

    private String userPwRe; // 비밀번호 확인

    private String userNm;  // 회원명

    private String userNick;  // 닉네임

    private String mobile;  // 핸드폰

    public static MemberInfoRequest entityToDto(Member member){

        MemberInfoRequest request = MemberInfoRequest.builder()
                .userNo(member.getUserNo())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .userNm(member.getUserNm())
                .userNick(member.getUserNick())
                .mobile(member.getMobile())
                .build();

        return  request;

    }


}
