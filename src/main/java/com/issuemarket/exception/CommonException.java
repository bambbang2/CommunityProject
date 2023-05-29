package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

/**
 * 공통 예외 ( messages 사용 )
 */

public class CommonException extends RuntimeException {

    // validations 메세지 코드
    protected static ResourceBundle bundleValidation;

    // errors 메세지 코드
    protected static ResourceBundle bundleError;
    protected HttpStatus httpStatus;

    static {
        bundleValidation = ResourceBundle.getBundle("messages.validations");
        bundleError = ResourceBundle.getBundle("messages.errors");
    }

    public CommonException(String message) {
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // 500 에러
    }

    public CommonException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}
