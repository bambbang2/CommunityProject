package com.itsue.exception;

import com.itsue.dto.MemberInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. 세션에 담긴 로그인 에러 메세지 삭제 (세션 초기화)

        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("requiredUserId");
        session.removeAttribute("requiredUserPw");
        session.removeAttribute("global");

        // 2. 로그인한 회원 정보 쉽게 확인하도록 세션 처리

        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        session.setAttribute("memberInfo", memberInfo);

        String url = request.getContextPath() + "/"; // 메인으로 이동
        response.sendRedirect(url);
    }
}