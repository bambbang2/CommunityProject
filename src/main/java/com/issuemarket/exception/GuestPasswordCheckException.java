package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

public class GuestPasswordCheckException extends CommonException {
    public GuestPasswordCheckException() {
        super("GuestPw.notChecked", HttpStatus.UNAUTHORIZED);
    }
}
