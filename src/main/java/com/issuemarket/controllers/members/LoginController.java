package com.issuemarket.controllers.members;

import com.issuemarket.dto.MemberJoin;
import com.issuemarket.service.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {

    private final MemberSaveService saveService;

    @GetMapping("/join")
    public String join(@ModelAttribute MemberJoin memberJoin, Model model) {
        commonProcess(model);

        return "member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid MemberJoin memberJoin, Errors errors, Model model) {
        commonProcess(model);

        if(errors.hasErrors()) {
            return "member/join";
        }

        saveService.save(memberJoin);

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(){

        return "member/login";
    }

    private void commonProcess(Model model) {
        model.addAttribute("pageTitle", "회원가입");
    }


}