package com.issuemarket.service.admin.post;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.entities.Post;
import com.issuemarket.entities.PostView;
import com.issuemarket.repositories.PostRepository;
import com.issuemarket.repositories.PostViewRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateHitService {

    private final PostViewRepository postViewRepository;
    private final PostRepository postRepository;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;

    public void update(Long id) {
        try {
            PostView postView = new PostView();
            postView.setId(id);
            postView.setUid(""+getUid());
            postViewRepository.saveAndFlush(postView);

        } catch (Exception e) {}

        long cnt = postViewRepository.getHit(id);
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setHit((int) cnt);
            postRepository.flush();
        }

    }

    private int getUid() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return memberUtil.isLogin() ? memberUtil.getMember().getUserNo().intValue() : Objects.hash(ip, ua);
    }

}
