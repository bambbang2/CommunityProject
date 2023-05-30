package com.issuemarket.validators;

public interface RequiredValidator {

    default void requiredCheck(String str, RuntimeException e) {
        if (str == null || str.isBlank()) {
            throw e;
        }
    }
}
