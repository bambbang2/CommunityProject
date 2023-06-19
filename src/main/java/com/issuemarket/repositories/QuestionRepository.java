package com.issuemarket.repositories;

import com.issuemarket.entities.Member;
import com.issuemarket.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByMember(Member member);
}
