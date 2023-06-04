package com.issuemarket.service.front.member;

import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyPageService {
    @Autowired
    MyPageRepository myPageRepository;

    public Optional<Member> getMember(String userId){

        Optional<Member> member = myPageRepository.findByUserId(userId);

       return member;
    }
}