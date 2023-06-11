package com.issuemarket.dto;

import com.issuemarket.commons.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberSearch {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eDate;

    private String userId;
    private String userNm;
    private String userNick;
    private String mobile;
    private Role role;

    private int page = 1;
    private int noOfRows = 20;

    private String sopt; // 검색 조건
    private String skey; // 검색 키워드

}