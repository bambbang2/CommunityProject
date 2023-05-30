package com.issuemarket.validators.member;

import com.issuemarket.dto.MemberJoin;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.validators.MobileValidator;
import com.issuemarket.validators.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileValidator, PasswordValidator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberJoin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 1. 아이디 중복 여부
         * 2. 비밀번호 복잡성 체크 : 알파벳(대문자, 소문자 포함), 숫자, 특수문자 포함
         * 3. 비밀번호와 비밀번호 확인 일치
         * 4. 휴대전화번호 ( 선택 ) : 입력된 경우 형식 체크
         * 5. 휴대전화번호가 입력된 경우 숫자만 추출해서 다시 커맨드 객체에 저장
         * 6. 필수 약관 동의 체크
         */

        MemberJoin memberJoin = (MemberJoin) target;
        String userId = memberJoin.getUserId();
        String userPw = memberJoin.getUserPw();
        String userPwRe = memberJoin.getUserPwRe();
        String mobile = memberJoin.getMobile();
        boolean[] agrees = memberJoin.getAgrees(); // 필수 약관

        // 1. 아이디 중복 여부
        if (userId != null && !userId.isBlank() && memberRepository.exist(userId)) {
            errors.rejectValue("userId", "Validation.duplicate.userId");
        }

        // 2. 비밀번호 복잡성 체크 : 알파벳(대문자, 소문자 포함), 숫자, 특수문자 포함 ( PasswordValidator 사용 )
        if (userPw != null && !userPw.isBlank()
                && (!alphaCheck(userPw,false)
                || !numberCheck(userPw)
                || !specialCharsCheck(userPw))) {
            errors.rejectValue("userPw", "Validation.complexity.password");
        }

        // 3. 비밀번호와 비밀번호 확인 일치
        if (userPw != null && !userPw.isBlank() && userPwRe != null && !userPwRe.isBlank() && !userPw.equals(userPwRe)) {
            errors.rejectValue("userPwRe", "Validation.incorrect.userPwRe");
        }

        // 4. 휴대전화번호 ( 선택 ) : 입력된 경우 형식 체크
        // 5. 휴대전화번호가 입력된 경우 숫자만 추출해서 다시 커맨드 객체에 저장
        if (mobile != null && !mobile.isBlank()) {
            if (!mobileNumCheck(mobile)) {
                errors.rejectValue("mobile", "Validation.mobile");
            }
            mobile = mobile.replaceAll("\\D", "");
            memberJoin.setMobile(mobile);
        }

        // 6. 필수 약관 동의 체크
        if (agrees != null && agrees.length > 0) { // 동의한 약관이 1개 이상일 때
            for (boolean agree : agrees) {
                if (!agree) {
                    errors.reject("Validation.memberJoin.agree");
                    break;
                }
            }
        }
    }
}