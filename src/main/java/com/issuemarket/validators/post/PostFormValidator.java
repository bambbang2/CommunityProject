package com.issuemarket.validators.post;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.PostForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PostFormValidator implements Validator {

    private final MemberUtil memberUtil;
    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostForm postForm = (PostForm) target;

        // guestPw check
        Long id = postForm.getId();
        Long userNo = postForm.getUserNo();

        if ((id == null && !memberUtil.isLogin()) || (id != null && userNo == null)) { // 작성시 비회원 || 수정시 비회원
            String guestPw = postForm.getGuestPw();
            if (guestPw == null || guestPw.isBlank()) {
                errors.rejectValue("guestPw", "NotBlank");
            }

            if (guestPw != null && guestPw.length() < 6) {
                errors.rejectValue("guestPw", "Size");
            }
        }

    }
}
