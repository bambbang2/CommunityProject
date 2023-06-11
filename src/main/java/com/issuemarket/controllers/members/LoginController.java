package com.issuemarket.controllers.members;

import com.issuemarket.dto.MemberLogin;
import com.issuemarket.dto.MemberSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {

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

    @GetMapping("/find/id")
    public String findId(@ModelAttribute MemberSearch memberSearch, Model model) {
        commonProcess(model, "아이디 찾기");

        return "member/findid";
    }

//    @PostMapping("/find/id")
//    public String findIdPs(Model model) {
//
//    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}
