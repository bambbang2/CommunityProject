package com.issuemarket.controllers.admins;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminMainController")
@RequestMapping("/admin")
public class MainController {

    @GetMapping
    public String index(){

        return "admin/index";
    }

    @GetMapping("/board")
    public String board(Model model){

        model.addAttribute("addCss",new String[]{"front/style"});
        return "layouts/front/board";
    }
}
