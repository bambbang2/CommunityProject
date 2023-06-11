package com.issuemarket.service.admin.post;

import com.issuemarket.dto.BoardSearch;
import com.issuemarket.entities.Post;
import com.issuemarket.entities.QPost;
import com.issuemarket.repositories.BoardRepository;
import com.issuemarket.repositories.PostRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostListService {

    private final PostRepository postRepository;

    private final BoardRepository boardRepository;


    public Page<Post> gets(BoardSearch boardSearch) {
        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int pageSize = boardSearch.getPageSize();

        page = page < 1 ? 1 : page;
        pageSize = pageSize < 1 ? 20 : pageSize;

        String sopt = boardSearch.getSopt();
        String skey = boardSearch.getSkey();

        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            sopt = sopt.trim();
            skey = skey.trim();

            if (sopt.equals("subject_content")) {
                builder.and(post.subject.contains(skey))
                        .or(post.content.contains(skey));
            } else if (sopt.equals("subject")) {
                builder.and(post.subject.contains(skey));
            } else if (sopt.equals("content")) {
                builder.and(post.content.contains(skey));
            } else if (sopt.equals("userNick")) {
                builder.and(post.member.userNick.contains(skey));
            }
        }

        Pageable pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> postList = postRepository.findAll(builder, pageable);

        return postList;
    }

}
