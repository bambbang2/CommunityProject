package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

public class BoardNotAllowAccessException extends CommonException{

    public BoardNotAllowAccessException() {
        super(bundleValidation.getString("Validation.board.NotAllowAccess"), HttpStatus.UNAUTHORIZED);
    }
}
