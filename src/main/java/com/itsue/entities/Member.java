package com.itsue.entities;

import com.itsue.commons.constants.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="seq_member", sequenceName = "seq_member", allocationSize = 1)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
    private Long userNo;

    private String userId;

    private String userPw;

    @Enumerated(EnumType.STRING)
    private Role roles = Role.USER;
}
