package com.issuemarket.controllers.members;

import com.issuemarket.dto.MemberLogin;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String login(String success, /*@CookieValue(value = "userId", required = false) Cookie cookie,*/ Model model){

        commonProcess(model);

        MemberLogin memberLogin = new MemberLogin();

//        if (cookie != null) {
//            memberLogin.setUserId(cookie.getValue());
//            memberLogin.setSavedId(true);
//        }
//
//        memberLogin.setSuccess(success);

        model.addAttribute("memberLogin", memberLogin);

        return "member/login";
    }

    @PostMapping("/login")
    public String loginPs(MemberLogin memberLogin, Errors errors, Model model/*, HttpServletResponse response*/) {

        commonProcess(model);

//        Cookie cookie = new Cookie("userId", memberLogin.getUserId());
//        cookie.setPath("/");
//
//        if (memberLogin.isSavedId()) {
//            cookie.setMaxAge(60 * 60 * 24 * 15);
//
//        } else {
//            cookie.setMaxAge(0);
//        }
//
//        response.addCookie(cookie);

        return "redirect:/front/index";
    }

    private void commonProcess(Model model) {
        model.addAttribute("pageTitle", "로그인");
    }
}
