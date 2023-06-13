package com.issuemarket.controllers.members;

import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.MemberLogin;
import com.issuemarket.dto.MemberSearch;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.admin.member.MemberUpdateService;
import com.issuemarket.service.front.member.MemberSearchService;
import com.issuemarket.validators.member.JoinValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
            String userId = searchService.idSearch(userNm, mobile) + "입니다.";

            String script = String.format("Swal.fire('찾으시는 아이디는', '%s', 'success').then(function() {history.go(-2);})", userId);

            model.addAttribute("script", script);

        } catch (Exception e) {
            e.printStackTrace();

            String script = String.format("Swal.fire('아이디를 찾을 수 없습니다.', '%s', 'error').then(function() {history.go(-1);})", "");

            model.addAttribute("script", script);

            return "commons/sweet_script";
        }
        return "commons/sweet_script";
    }

    @GetMapping("/find/pw")
    public String findPw(@ModelAttribute MemberSearch memberSearch, Model model) {
        commonProcess(model, "비밀번호 찾기");

        return "member/findpw";
    }

    @PostMapping("/find/pw")
    public String findPwPs(@ModelAttribute MemberSearch memberSearch, Model model) {
        commonProcess(model, "비밀번호 찾기");

        String userId = memberSearch.getUserId();
        String userNm = memberSearch.getUserNm();
        String mobile = memberSearch.getMobile();

        Long no = searchService.pwSearch(userId, userNm, mobile);

        return "redirect:/member/resetpw/" + no;
    }

    @GetMapping("/resetpw/{no}")
    public String resetPw(@PathVariable Long no, @ModelAttribute MemberJoin memberJoin, Errors errors, Model model) {
        commonProcess(model, "새 비밀번호 설정");

        return "member/resetpw";
    }

    @PostMapping("/resetpw/{no}")
    public String resetPwPs(@PathVariable Long no, @ModelAttribute MemberJoin memberJoin, Errors errors, Model model) {

        System.out.println("pwwwwww : " + no);

        String pw = memberJoin.getUserPw();

        System.out.println("pwwwwww : " + pw);

        try {
            joinValidator.validate(memberJoin, errors);

            if (errors.hasErrors()) {
                return "member/resetpw";
            }

            updateService.pwUpdate(no, pw);

        } catch (Exception e) {
            e.printStackTrace();
            return "member/findpw";
        }
        return "redirect:/member/login";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}
