package com.issuemarket.tests;

import com.issuemarket.dto.PostForm;
import com.issuemarket.entities.Board;
import com.issuemarket.entities.BoardForm;
import com.issuemarket.entities.Post;
import com.issuemarket.exception.PostNotExistException;
import com.issuemarket.models.board.PostInfoService;
import com.issuemarket.service.admin.board.config.BoardConfigInfoService;
import com.issuemarket.service.admin.board.config.BoardConfigSaveService;
import com.issuemarket.service.admin.post.PostSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class BoardViewTest {

    private Long id; // 게시글 번호
    private Board board;
    private PostForm postForm;
    @Autowired
    private PostSaveService postSaveService;
    @Autowired
    private BoardConfigSaveService boardConfigSaveService;
    @Autowired
    private BoardConfigInfoService boardConfigInfoService;
    @Autowired
    private PostInfoService postInfoService;

    @BeforeEach
    void init() {
        // 게시판 추가
        BoardForm boardForm = new BoardForm();
        boardForm.setBId("freetalk");
        boardForm.setBName("자유게시판");
        boardConfigSaveService.save(boardForm);

        board = boardConfigInfoService.get(boardForm.getBId(), true);

        // 테스트용 게시글 추가
        postForm = PostForm.builder()
                .bId(board.getBId())
                .gid(UUID.randomUUID().toString())
                .poster("작성자!")
                .guestPw("123456")
                .subject("제목#")
                .content("내용#")
                .category(board.getCategory() == null ? null : board.getCategories()[0])
                .build();

        postSaveService.save(postForm);
        id = postForm.getId();

    }


    @Test
    @DisplayName("게시글 조회 성공시 예외없음")
    void getPostSuccessTest() {
        assertDoesNotThrow(() -> {
           postInfoService.get(id);
        });
    }


    @Test
    @DisplayName("등록되지 않은 게시글이면 예외발생")
    void getPostNotExistTest() {
        assertThrows(PostNotExistException.class, () -> {
            postInfoService.get(id + 10);
        });
    }

}
