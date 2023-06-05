package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CommonException {
    public MemberNotFoundException() {
        super(bundleValidation.getString("Validation.member.notExists"), HttpStatus.BAD_REQUEST);
    }
}
