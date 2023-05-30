package com.issuemarket.validators;

public interface LengthValidator {
    /**
     * 문자열 체크
     */
    default void lengthCheck(String str, int min, int max, RuntimeException e) {

        if (str == null || (min > 0 && str.length() < min) || (max > 0 && str.length() > max)) {
            throw e;
        }
    }

    default void lengthCheck(String str, int min, RuntimeException e) {
        lengthCheck(str, min, 0, e);
    }

    /**
     * 숫자 범위 체크
     */
    default void lengthCheck(long num, int min, int max, RuntimeException e) {
        if (num < min || num > max) {
            throw e;
        }
    }

    default void lengthCheck(long num, int min, RuntimeException e) {
        if (num < min) {
            throw e;
        }
    }
}
