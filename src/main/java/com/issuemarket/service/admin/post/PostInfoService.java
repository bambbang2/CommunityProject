package com.issuemarket.service.admin.post;


import com.issuemarket.entities.Board;
import com.issuemarket.entities.Post;
import com.issuemarket.exception.PostNotExistException;
import com.issuemarket.repositories.PostRepository;
import com.issuemarket.service.admin.board.config.BoardConfigInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostInfoService {

    private final PostRepository postRepository;
    private final BoardConfigInfoService boardConfigInfoService;


    public Post get(Long id) {
        return get(id, "view");
    }

    public Post get(Long id, String location) {

        Post post = postRepository.findById(id).orElseThrow(PostNotExistException::new);

        boardConfigInfoService.get(post.getBoard().getBId(), location); // 설정 조회 + 접근 체크

        return post;
    }

    public List<Post> gets(String bId, String location) {

        Board board = boardConfigInfoService.get(bId, location);

        List<Post> postList = postRepository.findAll();

        return postList;
    }
}
