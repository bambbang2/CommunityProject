package com.issuemarket.entities;

import com.issuemarket.commons.constants.QuestionCategory;
import com.issuemarket.commons.constants.QuestionStatus;
import com.issuemarket.commons.constants.QuestionType;
import com.issuemarket.dto.QuestionForm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "question")
@Data
public class Question extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Column(name = "question_category")
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Column(name = "question_title")
    private String title;

    @Column(name = "quesition_content")
    private String content;

    @Column(name ="question_status")
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;


}
