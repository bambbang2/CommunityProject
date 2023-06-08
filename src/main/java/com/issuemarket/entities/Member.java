package com.issuemarket.entities;

import com.issuemarket.commons.constants.Role;
import com.issuemarket.dto.MemberInfoRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="seq_member", sequenceName = "seq_member", allocationSize = 1)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
    private Long userNo;  // 회원 번호 ( 회원이 입력 X )

    @Column(length = 40, unique = true, nullable = false)
    private String userId;  // 아이디

    @Column(length = 60, nullable = false)
    private String userPw;  // 비밀번호

    @Column(length = 40, nullable = false)
    private String userNm;  // 회원명

    @Column(length = 40, unique = true)
    private String userNick;  // 닉네임

    @Column(length = 11, nullable = false)
    private String mobile;  // 핸드폰

    @Lob
    private String termsAgree;  // 약관 동의 내역

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role roles = Role.USER;  // 권한 ( 기본 - 회원 )

    public void updateMember(MemberInfoRequest memberInfoRequest, PasswordEncoder passwordEncoder){
        if(memberInfoRequest.getUserNewPw() != null){
            String encodedPassword = memberInfoRequest.getUserNewPw();
            this.userPw = passwordEncoder.encode(memberInfoRequest.getUserNewPw());
        }
        this.userNm = memberInfoRequest.getUserNm();
        this.userNick = memberInfoRequest.getUserNick();
        this.mobile = memberInfoRequest.getMobile();
    }

}
