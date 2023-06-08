package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

public class PostNotExistException extends CommonException{
    public PostNotExistException() {
        super(bundleValidation.getString("Validation.post.notExists"), HttpStatus.BAD_REQUEST);
    }
}
