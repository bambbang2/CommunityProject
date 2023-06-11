package com.issuemarket.controllers.boards;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.dto.PostForm;
import com.issuemarket.entities.Board;
import com.issuemarket.entities.BoardForm;
import com.issuemarket.entities.Member;
import com.issuemarket.entities.Post;
import com.issuemarket.exception.BoardNotAllowAccessException;
import com.issuemarket.exception.CommonException;
import com.issuemarket.exception.GuestPasswordCheckException;
import com.issuemarket.service.admin.board.config.BoardConfigInfoService;
import com.issuemarket.service.admin.post.GuestPasswordCheckService;
import com.issuemarket.service.admin.post.PostInfoService;
import com.issuemarket.service.admin.post.PostSaveService;
import com.issuemarket.service.admin.post.UpdateHitService;
import com.issuemarket.validators.post.PostFormValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class UserBoardController {

    private final BoardConfigInfoService boardConfigInfoService;
    private final PostSaveService postSaveService;
    private final HttpServletResponse response;
    private final MemberUtil memberUtil;
    private final PostFormValidator postFormValidator;
    private final PostInfoService postInfoService;
    private final UpdateHitService updateHitService;
    private final GuestPasswordCheckService guestPwCheckService;
    private final HttpSession session;

    private Board board;

    @GetMapping("/list/{bId}")
    public String list(@PathVariable String bId, @RequestParam(value = "category", required = false) String categoryName , Model model) {
        commonProcess(bId, "list", model);

        Board board = boardConfigInfoService.get(bId, "list");

//        Post post = postInfoService.get


        return "board/list";
    }

    @GetMapping("/write/{bId}")
    public String write(@PathVariable String bId, @ModelAttribute PostForm postForm, Model model) {
        commonProcess(bId, "write", model);
        postForm.setBId(bId);
        if (memberUtil.isLogin()) {
            postForm.setPoster(memberUtil.getMember().getUserNick());
        }


        postForm.setBId(bId);

        return "board/write";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Post post = postInfoService.get(id, "update");
        board = post.getBoard();
        commonProcess(board.getBId(), "update", model);

        updateDeletePossibleCheck(post);

        PostForm postForm = new ModelMapper().map(post, PostForm.class);
        if (post.getMember() == null) {
            board.setGuest(true);
        }
        model.addAttribute("postForm", postForm);

        return "board/update";
    }

    @PostMapping("/save")
    public String save(@Valid PostForm postForm, Errors errors, Model model){
        Long id = postForm.getId();
        String mode = "write";
        if (id != null) {
            mode = "update";
            Post post = postInfoService.get(id);
            board = post.getBoard();
            if (post.getMember() == null) {
                board.setGuest(true);
            } else {
                postForm.setUserNo(post.getMember().getUserNo());
            }

            updateDeletePossibleCheck(post);
        }
        commonProcess(postForm.getBId(), mode, model);

        postFormValidator.validate(postForm, errors);

        if (errors.hasErrors()) {
            return "board/" + mode;
        }
        postSaveService.save(postForm);

        // 카테고리 부분이랑 수정 필요
        String location = board.getLocationAfterWriting();
        String url = "redirect:/board/";
        url += location.equals("view") ? "view/" + postForm.getId() : "list/" + postForm.getBId();

        return url;
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {
        Post post = postInfoService.get(id);
        board = post.getBoard();
        commonProcess(board.getBId(), "view", model);

        model.addAttribute("post", post);
        model.addAttribute("board", board);

        updateHitService.update(id);

        return "board/view";
    }

    @PostMapping("/password")
    public String passwordCheck(String password) {
        String mode = (String) session.getAttribute("guestPwMode");
        Long id = (Long) session.getAttribute("guestPwId");
        // 비회훤 비밀번호 검증
        guestPwCheckService.check(id, password, mode);

        // 비회원 비밀번호 검증 완료 처리
        session.setAttribute(mode + "_" + id, true);

        // 비번 확인 후 이동 경로
        String url = mode == "comment" ? "/board/comment/" : "/board/view/";
        url += id;

        // 검증 완료 후 세션 제거
        session.removeAttribute("guestPwMode");
        session.removeAttribute("guestPwId");

        return "redirect:" + url;
    }

    private void commonProcess(String bId, String action, Model model) {

        board = boardConfigInfoService.get(bId, action);
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        addCss.add("board/style");

        if (action.equals("write") || action.equals("update")) { // 글 쓰기에 필요한 js, css
            if (board.isUseEditor()) {
                addScript.add("ckeditor/ckeditor");
            }

            addScript.add("board/form");
        }

        model.addAttribute("board", board); // 게시판 설정
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
    }

    public void updateDeletePossibleCheck(Post post) {
        if (memberUtil.isAdmin()) {
            return;
        }

        Member member = post.getMember();

        if (member == null) {
            // 세션키 (board_postId) 있으면 검증 완료
            if (session.getAttribute("board_" + post.getId()) == null) {
                session.setAttribute("guestPwMode", "board");
                session.setAttribute("guestPwId", post.getId());

                throw new GuestPasswordCheckException();
            }

        } else { // 글 작성한 회원만

        }

        if (memberUtil.isLogin() &&
                memberUtil.getMember().getUserNo() != post.getMember().getUserNo()) {
            throw new BoardNotAllowAccessException();
        }


    }

    public void updateDeletePossibleCheck(Long id) {
        Post post = postInfoService.get(id, "update");
        updateDeletePossibleCheck(post);
    }

    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model) {
        e.printStackTrace();
        String msg = e.getMessage();

        HttpStatus status = e.getStatus();
        response.setStatus(status.value());

        if (e instanceof GuestPasswordCheckException) { // 비회원 비밀번호 검증 예외
            return "board/password";
        }

        String script = String.format("alert('%s');history.back();", msg);
        model.addAttribute("script", script);

        return  "commons/execute_script";
    }
}
