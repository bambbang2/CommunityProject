package com.issuemarket.service.admin.post;

import com.issuemarket.entities.Post;
import com.issuemarket.exception.GuestPasswordIncorrectException;
import com.issuemarket.exception.PostValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestPasswordCheckService {

    private final PostInfoService postInfoService;
    private final PasswordEncoder passwordEncoder;
    public void check(Long id, String password) {
        check(id, password, "board");
    }
    public void check(Long id, String password, String mode) {
        if (id == null) {
            throw new PostValidationException("BadRequest");
        }

        mode = mode == null || mode.isBlank() ? "board" : mode;

        if (mode.equals("board")) {
            Post post = postInfoService.get(id, "update");
            String guestPw = post.getGuestPw();
            boolean matched = passwordEncoder.matches(password, post.getGuestPw());
            if (!matched) {
                throw new GuestPasswordIncorrectException();
            }

        } else if (mode.equals("comment")) {

        }
    }
}
