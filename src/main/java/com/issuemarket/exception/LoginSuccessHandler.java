package com.issuemarket.exception;

import com.issuemarket.dto.MemberInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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

        // 아이디 쿠키 저장
        String savedId = request.getParameter("savedId");
        Cookie cookie = new Cookie("savedId", memberInfo.getUserId());
        if (savedId == null) { // 쿠키 삭제
            cookie.setMaxAge(0);
        } else { // 쿠키 등록
            cookie.setMaxAge(60 * 60 * 24 * 365);
        }
        response.addCookie(cookie);

        String url = request.getContextPath() + "/"; // 메인으로 이동
        response.sendRedirect(url);
    }
}