package com.itsue.controllers.admins;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminMainController")
@RequestMapping("/admin")
public class MainController {

    @GetMapping
    public String index(){

        return "admin/index";
    }

    @GetMapping("/config")
    public String config(){

        return "admin/config";
    }
}
