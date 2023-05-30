package com.issuemarket.commons;

import com.issuemarket.commons.constants.Role;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.entities.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    public boolean isLogin() {
        return getMember() != null;
    }

    public boolean isAdmin() {

        return isLogin() && getMember().getRoles() == Role.ADMIN;
    }

    public MemberInfo getMember() {
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");

        return memberInfo;
    }

    public Member getEntity() {
        if (isLogin()) {
            Member member = new ModelMapper().map(getMember(), Member.class);
            return member;
        }

        return null;
    }
}
