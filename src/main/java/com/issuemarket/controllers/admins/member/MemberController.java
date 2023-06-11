package com.issuemarket.controllers.admins.member;

import com.issuemarket.commons.constants.Role;
import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.MemberSearch;
import com.issuemarket.entities.Member;
import com.issuemarket.exception.CommonException;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.admin.member.MemberDeleteService;
import com.issuemarket.service.admin.member.MemberListService;
import com.issuemarket.service.admin.member.MemberUpdateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberListService listService;
    private final MemberRepository memberRepository;
    private final MemberDeleteService deleteService;
    private final HttpServletResponse response;
    private final MemberUpdateService updateService;

    @GetMapping
    public String list(@ModelAttribute MemberSearch search, Model model) {
        commonProcess(model, "회원관리");

        Page<Member> members = listService.gets(search);
        model.addAttribute("items", members.getContent());

        String[] roles = Arrays.stream(Role.values()).map(r->r.toString()).toArray(String[]::new);
        model.addAttribute("roles", roles);

        return "admin/member/index";
    }

    @PostMapping
    public String listPs(@ModelAttribute Member member, Model model) {
        commonProcess(model, "회원 관리");
        System.out.println("Before member " + member);
        String updateRole = String.valueOf(member.getRoles());
        member = listService.get(member.getUserNo());

        member.setRoles(Role.valueOf(updateRole));

        memberRepository.saveAndFlush(member);
        System.out.println("After member " + member);
        return "redirect:/admin/member";
    }


    @GetMapping("/view/{userNo}")
    public String view(@PathVariable Long userNo, Model model) {
        commonProcess(model, "회원 상세 조회");
        
        Member member = memberRepository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        model.addAttribute("member", member);

        return "admin/member/view";
    }

    @PostMapping("/view/{userNo}")
    public String viewPs(@PathVariable Long userNo, @ModelAttribute("member") MemberJoin memberJoin, Model model) {
        commonProcess(model, "회원 상세 조회");

        updateService.update(userNo, memberJoin);

        return "redirect:/admin/member";
    }



    @GetMapping("/delete/{userNo}")
    public String delete(@PathVariable Long userNo) {
        Member member = listService.get(userNo);

        deleteService.delete(member.getUserNo());

        return "redirect:/admin/member";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", "member");
    }

    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model) {
        e.printStackTrace();

        String message = e.getMessage();
        HttpStatus status = e.getStatus();
        response.setStatus(status.value());

        String script = String.format("alert('%s');history.back();", message);
        model.addAttribute("script", script);

        return "commons/execute_script";
    }
}