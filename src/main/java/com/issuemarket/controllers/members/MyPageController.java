package com.issuemarket.controllers.members;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.entities.Member;
import com.issuemarket.service.front.member.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/member")
public class MyPageController {
    @Autowired
    MyPageService myPageService;

    @Autowired
    private MemberUtil memberUtil;


    @GetMapping("/myinfo")
    public String myPage( Model model) {

        MemberInfo memberInfo = memberUtil.getMember();
        Optional<Member> _member = myPageService.getMember(memberInfo.getUserId());

   System.out.println("값이 담겼는지 확인하겠습니다"+_member);

        Member member = _member.orElse(null);
    model.addAttribute("member",member);


        return "/member/myinfo";
    }
}
