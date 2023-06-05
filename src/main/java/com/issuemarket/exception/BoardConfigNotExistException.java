package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

public class BoardConfigNotExistException extends CommonException{
    public BoardConfigNotExistException() {
        super(bundleValidation.getString("Validation.board.notExists"), HttpStatus.BAD_REQUEST);
    }
}
