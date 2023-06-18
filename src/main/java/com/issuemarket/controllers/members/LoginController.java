package com.issuemarket.controllers.members;

import com.issuemarket.dto.MemberLogin;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.admin.member.MemberUpdateService;
import com.issuemarket.service.front.member.MemberSearchService;
import com.issuemarket.validators.member.JoinValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository repository;
    private final MemberSearchService searchService;
    private final JoinValidator joinValidator;
    private final MemberUpdateService updateService;

    @GetMapping("/login")
    public String login(@CookieValue(value = "savedId", required = false) String savedId, Model model){

        commonProcess(model, "로그인");

        MemberLogin memberLogin = new MemberLogin();

        if (savedId != null) {
            memberLogin.setSavedId(true);
            memberLogin.setUserId(savedId);
        }
        model.addAttribute("memberLogin", memberLogin);

        return "member/login";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}
