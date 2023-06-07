package com.issuemarket.validators.post;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.PostForm;
import com.issuemarket.exception.PostValidationException;
import com.issuemarket.validators.LengthValidator;
import com.issuemarket.validators.RequiredValidator;
import com.issuemarket.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator implements Validator<PostForm>, RequiredValidator, LengthValidator {

    private final MemberUtil memberUtil;

    @Override
    public void check(PostForm postForm) {
        requiredCheck(postForm.getBId(), new PostValidationException("BadRequest"));
        requiredCheck(postForm.getGid(), new PostValidationException("BadRequest"));
        requiredCheck(postForm.getPoster(), new PostValidationException("NotBlank.postForm.poster"));
        requiredCheck(postForm.getSubject(), new PostValidationException("NotBlank.postForm.subject"));
        requiredCheck(postForm.getContent(), new PostValidationException("NotBlank.postForm.content"));

        if (!memberUtil.isLogin()) {
            requiredCheck(postForm.getGuestPw(), new PostValidationException("NotBlank.postForm.guestPw"));

            // guestPw length check
            lengthCheck(postForm.getGuestPw(), 6, new PostValidationException("Size.postForm.guestPw"));
        }


    }
}
