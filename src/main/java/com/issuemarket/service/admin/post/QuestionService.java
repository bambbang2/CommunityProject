package com.issuemarket.service.admin.post;

import com.issuemarket.dto.QuestionForm;
import com.issuemarket.entities.Question;
import com.issuemarket.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;    //생성자 주입으로 Question Repository 사용 가능

    public void saveQuestion(QuestionForm questionForm){
        Question question = questionForm.createQuestion();

        questionRepository.save(question);
    }
}
