package com.issuemarket.controllers.members;

import com.issuemarket.dto.social.ProfileResult;
import com.issuemarket.dto.social.SocialLogin;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/social")
@RequiredArgsConstructor
public class SocialLoginController {

    private final SocialLogin socialLogin;

    @GetMapping("/login")
    public String login(String code, HttpSession session) {
        if (code == null || code.isBlank()) {
            return "redirect:/member/login";
        }

        ProfileResult profileResult = socialLogin.getProfile(code);
        if (profileResult == null) {
            return "redirect:/member/login";
        }

        session.setAttribute("kakao", profileResult);

        // 이미 가입된 카카오 계정인 경우는 로그인
        if (socialLogin.exists("kakao", profileResult.getId())) {
            socialLogin.login("kakao", profileResult.getId());
            return "redirect:/";
        }

        // 가입 안된 계정인 경우는 회원 가입
        return "redirect:/member/join";
    }
}
