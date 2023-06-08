package com.issuemarket.models.board;

import com.issuemarket.entities.Post;
import com.issuemarket.exception.PostNotExistException;
import com.issuemarket.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostInfoService {

    private final PostRepository postRepository;
    public Post get(Long id) {

        Post post = postRepository.findById(id).orElseThrow(PostNotExistException::new);

        return null;
    }
}
