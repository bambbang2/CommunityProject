package com.issuemarket.controllers.boards;

import com.issuemarket.commons.constants.QuestionCategory;
import com.issuemarket.commons.constants.QuestionType;
import com.issuemarket.dto.QuestionForm;
import com.issuemarket.repositories.QuestionRepository;
import com.issuemarket.service.admin.post.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor//주입
@Slf4j
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    //문의 하기 메인
    @GetMapping({"/", ""})
    public String main(Model model, Principal principal){
        String userId = principal.getName();

        List<QuestionForm> questionFormList = questionService.getQuestionList(userId);

        log.info(questionFormList.toString());

        model.addAttribute("questionFormList", questionFormList);

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
    public String writeSubmit(@Valid QuestionForm questionForm, Principal principal, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()) {
            List<QuestionType> questionTypes = List.of(QuestionType.values());
            model.addAttribute("questionTypes", questionTypes);

            List<QuestionCategory> questionCategories = List.of(QuestionCategory.values());
            model.addAttribute("questionCategories", questionCategories);

            log.info("에러");
            return "question/writeForm";
        }

        questionService.saveQuestion(questionForm, principal);

        return "redirect:/question";
    }
}
