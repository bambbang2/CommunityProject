package com.issuemarket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FrontMainController {

    @GetMapping
    public String main() {


        return "front/index";
    }
}
