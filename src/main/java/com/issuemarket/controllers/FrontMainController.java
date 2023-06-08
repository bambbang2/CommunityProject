package com.issuemarket.controllers;

import com.issuemarket.entities.Board;
import com.issuemarket.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class FrontMainController {

    private final BoardRepository boardRepository;

    @GetMapping
    public String main(Model model) {

//        List<Board> boards = boardRepository.findAll();
//        model.addAttribute("boards", boards);

        return "front/index";
    }
}
