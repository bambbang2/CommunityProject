package com.issuemarket.service.admin.post;

import com.issuemarket.commons.constants.QuestionStatus;
import com.issuemarket.dto.QuestionForm;
import com.issuemarket.entities.Member;
import com.issuemarket.entities.Question;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    //생성자 주입으로 Question Repository 사용 가능

    public void saveQuestion(QuestionForm questionForm, Principal principal){
        Question question = questionForm.createQuestion();
        Member member = memberRepository.findByUserId(principal.getName());
        question.setMember(member);
        question.setStatus(QuestionStatus.WAIT);

        questionRepository.save(question);
    }
    
    // 질문 목록 조회
    public List<QuestionForm> getQuestionList(String userId){
        Member member = memberRepository.findByUserId(userId);

        List<Question> questionList = questionRepository.findByMember(member);

        List<QuestionForm> questionFormList = new ArrayList<>();

        for(Question question : questionList){
            QuestionForm questionForm = QuestionForm.of(question);

            questionFormList.add(questionForm);
        }

        return questionFormList;
    }
}
