package com.issuemarket.dto;

import com.issuemarket.commons.constants.QuestionCategory;
import com.issuemarket.commons.constants.QuestionType;
import com.issuemarket.entities.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;
import org.modelmapper.ModelMapper;

@Data
public class QuestionForm {

    private Long id;
    private QuestionType type;
    private QuestionCategory category;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    // DTO <-> 엔티티 간 변환을 도와주는 객체
    private static ModelMapper modelMapper = new ModelMapper();

    // DTO -> 엔티티 변환
    public Question createQuestion(){
        return modelMapper.map(this, Question.class);
    }

    // 엔티티 -> DTO 변환
    public static QuestionForm of(Question question){
        return modelMapper.map(question, QuestionForm.class);
    }
}
