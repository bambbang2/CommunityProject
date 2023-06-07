package com.issuemarket.service.admin.post;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.PostForm;
import com.issuemarket.entities.Board;
import com.issuemarket.entities.Post;
import com.issuemarket.repositories.PostRepository;
import com.issuemarket.service.admin.board.config.BoardConfigInfoService;
import com.issuemarket.validators.post.PostValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSaveService {

    private final PostValidator validator;
    private final MemberUtil memberUtil;
    private final BoardConfigInfoService boardConfigInfoService;
    private final PostRepository postRepository;
    private final HttpServletRequest request;

    public void save(PostForm postForm) {
        validator.check(postForm);

        Long id = postForm.getId();
        Board board = boardConfigInfoService.get(postForm.getBId(), id == null ? "write" : "update");

        Post post = null;
        if (id == null) { // write

            String ip = request.getRemoteAddr();
            String ua = request.getHeader("User-Agent");
            post = Post.builder()
                    .gid(postForm.getGid())
                    .board(board)
                    .category(postForm.getCategory())
                    .poster(postForm.getPoster())
                    .subject(postForm.getSubject())
                    .content(postForm.getContent())
                    .ip(ip)
                    .ua(ua)
                    .build();

            post.setBoard(board);

            if (memberUtil.isLogin()) post.setMember(memberUtil.getEntity());
            else {
                post.setGuestPw(postForm.getGuestPw());
            }

        } else { // update

        }

        post = postRepository.saveAndFlush(post);
        postForm.setId(post.getId());
    }
}
