package com.issuemarket.controllers.members;

import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.social.ProfileResult;
import com.issuemarket.service.front.member.MemberSaveService;
import com.issuemarket.validators.member.JoinValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class JoinController {

    private final MemberSaveService saveService;

    private final JoinValidator joinValidator;

    private final HttpSession session;

    @GetMapping("/join")
    public String join(@ModelAttribute MemberJoin memberJoin, Model model) {
        commonProcess(model);

        ProfileResult profileResult = (ProfileResult) session.getAttribute("kakao");
        if (profileResult != null) {
            memberJoin.setUserNm(profileResult.getProperties().getNickname());
        }

        return "member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid MemberJoin memberJoin, Errors errors, Model model) {
        commonProcess(model);
        joinValidator.validate(memberJoin, errors);

        if(errors.hasErrors()) {
            return "member/join";
        }

        saveService.save(memberJoin);

        return "redirect:/member/login";
    }

    private void commonProcess(Model model) {
        model.addAttribute("pageTitle", "회원가입");
    }
}