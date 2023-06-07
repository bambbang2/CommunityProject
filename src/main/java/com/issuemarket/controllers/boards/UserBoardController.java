package com.issuemarket.controllers.boards;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.PostForm;
import com.issuemarket.entities.Board;
import com.issuemarket.exception.CommonException;
import com.issuemarket.service.admin.board.config.BoardConfigInfoService;
import com.issuemarket.service.admin.post.PostSaveService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private Board board;

    @GetMapping("/list/{bId}")
    public String list(@PathVariable String bId, Model model) {
        commonProcess(bId, "list", model);


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
        commonProcess(null, "update", model);


        return "board/update";
    }

    @PostMapping("/save")
    public String save(@Valid PostForm postForm, Errors errors, Model model){
        Long id = postForm.getId();
        String mode = id == null ? "write" : "update";
        commonProcess(postForm.getBId(), mode, model);

        if (errors.hasErrors()) {
            return "board/" + mode;
        }
        postSaveService.save(postForm);


        String location = board.getLocationAfterWriting();
        String url = "redirect:/board/";
        url += location.equals("view") ? "view/" + postForm.getId() : "list/" + postForm.getId();

        return url;
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {
        commonProcess(null, "view", model);


        return "board/view";
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

    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model) {
        e.printStackTrace();
        String msg = e.getMessage();

        HttpStatus status = e.getStatus();
        response.setStatus(status.value());

        String script = String.format("alert('%s');history.back();", msg);
        model.addAttribute("script", script);

        return  "commons/execute_script";
    }
}
