package com.issuemarket.tests;

import com.issuemarket.commons.configs.ConfigSaveService;
import com.issuemarket.controllers.admins.ConfigForm;
import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.PostForm;
import com.issuemarket.entities.Board;
import com.issuemarket.entities.BoardForm;
import com.issuemarket.exception.PostValidationException;
import com.issuemarket.service.admin.post.PostSaveService;
import com.issuemarket.service.admin.board.config.BoardConfigInfoService;
import com.issuemarket.service.admin.board.config.BoardConfigSaveService;
import com.issuemarket.service.front.member.MemberSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("게시글 등록, 수정 테스트")
@Transactional
public class BoardSaveTests {

    @Autowired
    private PostSaveService postSaveService;

    @Autowired
    private BoardConfigSaveService boardConfigSaveService;

    @Autowired
    private BoardConfigInfoService boardConfigInfoService;

    @Autowired
    private MemberSaveService memberSaveService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConfigSaveService configSaveService;
    private Board board;
    private MemberJoin memberJoin;

    @BeforeEach
    @Transactional
    void init() {
        // 사이트 설정 등록
        configSaveService.save("siteConfig", new ConfigForm());

        // 게시판 추가
        BoardForm boardForm = new BoardForm();
        boardForm.setBId("freetalk10");
        boardForm.setBName("자유게시판");
        boardConfigSaveService.save(boardForm);

        board = boardConfigInfoService.get(boardForm.getBId(), true);

        // 회원 추가
        memberJoin = MemberJoin.builder()
                .userId("user01")
                .userPw("aA!123456")
                .userPwRe("aA!123456")
                .mobile("01012341234")
                .userNm("사용자01")
                .agrees(new boolean[]{true})
                .build();

        memberSaveService.save(memberJoin);
    }


    private PostForm getGuestPostForm() {
        PostForm postForm = getCommonPostForm();

        postForm.setGuestPw("12345678");

        return postForm;
    }


    private PostForm getCommonPostForm() {
        return PostForm.builder()
                .bId(board.getBId())
                .gid(UUID.randomUUID().toString())
                .poster(memberJoin.getUserNm())
                .subject("제목#")
                .content("내용#")
                .category(board.getCategory() == null ? null : board.getCategories()[0])
                .build();
    }

    @Test
    @DisplayName("비회원 게시글 등록 성공시 예외 없음")
    @WithAnonymousUser
    void registerGuestSuccessTest() {
        assertDoesNotThrow(() -> {
            postSaveService.save(getGuestPostForm());
        });
    }

    @Test
    @DisplayName("회원 게시글 등록 성공시 예외 없음")
    @WithMockUser(username="user01", password = "aA!123456")
    void registerMemberSuccessTest() {
        assertDoesNotThrow(() -> {
            postSaveService.save(getCommonPostForm());
        });
    }

    // 회원 & 비회원 공통
    private void commonRequiredFieldsTest() {
        assertAll(
                // bId - null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setBId(null);
                    postSaveService.save(postForm);
                }),
                // bId - 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setBId("      ");
                    postSaveService.save(postForm);
                }),
                // gid - null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setGid(null);
                    postSaveService.save(postForm);
                }),
                // gid - 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setGid("      ");
                    postSaveService.save(postForm);
                }),
                // poster - null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setPoster(null);
                    postSaveService.save(postForm);
                }),
                // poster - 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setPoster("      ");
                    postSaveService.save(postForm);
                }),
                // subject - null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setSubject(null);
                    postSaveService.save(postForm);
                }),
                // subject - 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setSubject("     ");
                    postSaveService.save(postForm);
                }),
                // content - null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setContent(null);
                    postSaveService.save(postForm);
                }),
                // content - 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getCommonPostForm();
                    postForm.setContent("     ");
                    postSaveService.save(postForm);
                })
        );
    }

    @Test
    @DisplayName("비회원일시 필수항목 검증 - bId, gid, poster, subject, content, guestPw, PostValidationException 발생")
    @WithAnonymousUser
    void requiredFieldsGuestTest() {
        commonRequiredFieldsTest();

        assertAll(
                () ->assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getGuestPostForm();
                    postForm.setGuestPw(null);
                    postSaveService.save(postForm);
                }),
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getGuestPostForm();
                    postForm.setGuestPw("      ");
                    postSaveService.save(postForm);
                }),
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getGuestPostForm();
                    postForm.setGuestPw("1234");
                    postSaveService.save(postForm);
                })

            );



    }


    @Test
    @DisplayName("회원일시 필수항목 검증 - bId, gid, poster, subject, content, PostValidationException 발생")
    @WithMockUser(username = "user01", password = "aA!123456")
    void requiredFieldsMemberTest() {
        commonRequiredFieldsTest();
    }


    @Test
    @DisplayName("비회원 게시글 작성 유효성 검사")
    void requiredFieldsGuestControllerTest() throws Exception {
        PostForm postForm = getGuestPostForm();
        String body = mockMvc.perform(post("/board/save")
                .param("bId", postForm.getBId())
                .param("gid", postForm.getGid())
                        .with(csrf().asHeader()))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset());

        ResourceBundle bundle = ResourceBundle.getBundle("messages.validations");
        String[] messages = {
                bundle.getString("NotBlank.postForm.poster"),
                bundle.getString("NotBlank.postForm.subject"),
                bundle.getString("NotBlank.postForm.content"),
                bundle.getString("NotBlank.postForm.guestPw"),
                bundle.getString("Size.postForm.guestPw")
        };

        for (String message : messages)  {
            assertTrue(body.contains(message));
        }

    }

}
