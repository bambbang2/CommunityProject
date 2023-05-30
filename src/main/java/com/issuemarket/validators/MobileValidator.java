package com.issuemarket.validators;

public interface MobileValidator {
    default boolean mobileNumCheck(String mobile) {

        mobile = mobile.replaceAll("\\D", ""); // 숫자 아닌 것 전부 제거
        String pattern = "^01[016]\\d{3,4}\\d{4}$";

        return mobile.matches(pattern);
    }
}
