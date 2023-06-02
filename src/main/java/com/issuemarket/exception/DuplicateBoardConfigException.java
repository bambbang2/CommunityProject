package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

public class DuplicateBoardConfigException extends CommonException{
    public DuplicateBoardConfigException() {
        super("#이미 등록된 게시판입니다.", HttpStatus.BAD_REQUEST);
    }
}
