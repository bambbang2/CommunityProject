package com.issuemarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberLogin {

    @NotBlank
    private String userId;

    @NotBlank
    private String userPw;

    private boolean savedId;

    private String success;
}
