package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

public class GuestPasswordIncorrectException extends CommonException{
    public GuestPasswordIncorrectException() {
        super(bundleValidation.getString("GuestPw.incorrect"), HttpStatus.BAD_REQUEST);
    }
}
