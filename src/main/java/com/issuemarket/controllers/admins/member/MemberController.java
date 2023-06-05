package com.issuemarket.controllers.admins.member;

import com.issuemarket.commons.constants.Role;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.dto.MemberSearch;
import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.admin.member.MemberListService;
import com.issuemarket.service.front.member.MemberInfoService;
import com.issuemarket.service.front.member.MemberSaveService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberInfoService memberInfoService;
    private final MemberListService memberListService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String gets(@ModelAttribute MemberSearch search, Model model) {
        commonProcess(model, "회원관리");

        Page<Member> members = memberListService.gets(search);
        model.addAttribute("items", members.getContent());

        return "admin/member/index";
    }

    @PostMapping
    public String getsPs(@ModelAttribute MemberInfo memberInfo, Model model) {
        commonProcess(model, "회원 관리");
        model.addAttribute("roles", Role.values());
        memberInfo = memberListService.get(memberInfo.getUserNo());

        Member member = new ModelMapper().map(memberInfo, Member.class);
        System.out.println(member);
        memberRepository.saveAndFlush(member);



        return "redirect:/admin/member";
    }



    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", "member");
    }
}
