package com.issuemarket.service;

import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberListService {

    private final MemberRepository memberRepository;


}
