package com.issuemarket.controllers.members;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.dto.MemberInfoRequest;
import com.issuemarket.dto.PostForm;
import com.issuemarket.entities.Member;
import com.issuemarket.service.admin.post.PostListService;
import com.issuemarket.service.front.member.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberUtil memberUtil;
    private final PostListService postListService;

    @GetMapping("/myinfo")
    public String myPage(Model model) {

        MemberInfo memberInfo = memberUtil.getMember();
        Optional<Member> _member = myPageService.getMember(memberInfo.getUserId());


        Member member = _member.orElse(null);
        MemberInfoRequest request = MemberInfoRequest.entityToDto(member);
        System.out.println(request);
        model.addAttribute("member", request);



        return "/member/myinfo";
    }

    // 정보 수정
    @PostMapping("/myinfo")
    public String myPage1(@ModelAttribute("member") MemberInfoRequest memberInfoRequest, Principal principal, Model model) {
        System.out.println(memberInfoRequest);

        try{
            myPageService.updateMember(memberInfoRequest, principal);
        }catch(IllegalStateException e) {
            model.addAttribute("errorMessage", "비밀번호가 같지 않습니다");
        }catch(IllegalArgumentException e){
            model.addAttribute("errorMessage", "아이디나 비밀번호가 틀립니다");
        }catch(Exception e){
            model.addAttribute("errorMessage", "서버 내 문제입니다. 관리자에게 문의해주세요");
        }

        return "/member/myinfo";
    }

    // 내가 쓴 글 확인
    @GetMapping("/mywrite")
    public String myWrite(Principal principal, Model model){
        List<PostForm> postFormList = myPageService.getPostsByUser(principal);

        model.addAttribute("postFormList", postFormList);

        return "/member/mywrite";
    }
}
