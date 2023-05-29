package com.issuemarket.exception;

import org.springframework.http.HttpStatus;

/**
 * 로그인 유효성 검사 오류
 */
public class LoginValidationException extends CommonException {

    private String field;

    /**
     * 일반 검증
     * 응답 코드 == 400대 오류
     * @param code
     */

    public LoginValidationException(String code) {
            super(bundleValidation.getString(code), HttpStatus.BAD_REQUEST);
        }

    /**
     * 필드를 가진 특정 검증
     * @param field
     * @param code
     */
    public LoginValidationException(String field, String code) {
            this(code);
            this.field = field;
    }

    public String getField() {
            return field;
        }
}

