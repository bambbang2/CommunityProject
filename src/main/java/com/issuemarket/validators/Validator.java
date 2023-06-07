package com.issuemarket.validators;

public interface Validator<T> {

    void check(T t);
}
