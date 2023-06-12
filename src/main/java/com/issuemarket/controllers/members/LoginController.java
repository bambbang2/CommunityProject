package com.issuemarket.controllers.members;

import com.issuemarket.dto.MemberLogin;
import com.issuemarket.dto.MemberSearch;
import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.front.member.MemberSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository repository;
    private final MemberSearchService searchService;

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

    @PostMapping("/find/id")
    public String findIdPs(@ModelAttribute MemberSearch memberSearch, Model model) {
        commonProcess(model, "아이디 찾기");

        String userNm = memberSearch.getUserNm();
        String mobile = memberSearch.getMobile();

        try {
            searchService.idSearch(userNm, mobile);

            Member member = repository.findByUserNmAndMobile(userNm, mobile);
            String userId = member.getUserId();

            String script = String.format("alert('%s, %s');history.back();", "찾으시는 아이디는 ", userId);
            model.addAttribute("script", script);
        } catch (Exception e) {
            e.printStackTrace();
            String script = String.format("alert('%s');history.back();", "아이디를 찾을 수 없습니다.");
            model.addAttribute("script", script);

            return  "commons/execute_script";
        }
        return  "commons/execute_script";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}
