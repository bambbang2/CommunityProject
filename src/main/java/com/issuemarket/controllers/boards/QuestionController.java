package com.issuemarket.controllers.boards;

import com.issuemarket.commons.constants.QuestionCategory;
import com.issuemarket.commons.constants.QuestionType;
import com.issuemarket.dto.QuestionForm;
import com.issuemarket.service.admin.post.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor//주입

public class QuestionController {
    private final QuestionService questionService;

    //문의 하기 메인
    @GetMapping({"/", ""})
    public String main(Model model){

        return "question/index";
    }
    //문의하기 등록폼
    @GetMapping("/write")
    public String writeForm(Model model){
        model.addAttribute("questionForm", new QuestionForm());

        List<QuestionType> questionTypes = List.of(QuestionType.values());
        model.addAttribute("questionTypes", questionTypes);

        List<QuestionCategory> questionCategories = List.of(QuestionCategory.values());
        model.addAttribute("questionCategories", questionCategories);

        return "question/writeForm";
    }
    //등록
    @PostMapping("/write")
    public String writeSubmit(@Valid QuestionForm questionForm, Errors errors){

        if(errors.hasErrors()) {
            return "question/writeForm";
        }

        questionService.saveQuestion(questionForm);

        return "question/index";
    }

}
