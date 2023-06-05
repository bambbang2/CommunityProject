package com.issuemarket.controllers.admins.board;


import com.issuemarket.commons.MenuForm;
import com.issuemarket.commons.Menus;
import com.issuemarket.dto.BoardSearch;
import com.issuemarket.entities.Board;
import com.issuemarket.entities.BoardForm;
import com.issuemarket.exception.CommonException;
import com.issuemarket.repositories.BoardRepository;
import com.issuemarket.service.admin.board.config.BoardConfigInfoService;
import com.issuemarket.service.admin.board.config.BoardConfigListService;
import com.issuemarket.service.admin.board.config.BoardConfigSaveService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("adminBoardController")
@RequestMapping("admin/board")
@RequiredArgsConstructor
public class BoardController {

    private final HttpServletRequest request;
    private final BoardRepository boardRepository;
    private final BoardConfigSaveService boardConfigSaveService;
    private final BoardConfigListService boardConfigListService;
    private final BoardConfigInfoService boardConfigInfoService;

    @GetMapping
    public String index(@ModelAttribute BoardSearch boardSearch, Model model){
        commonProcess(model, "게시판 목록");

        Page<Board> boards = boardConfigListService.gets(boardSearch);
        model.addAttribute("items", boards.getContent());

        return "admin/board/index";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute BoardForm boardForm, Model model){
        commonProcess(model, "게시판 등록");

        return "admin/board/boardConfig";
    }

    @GetMapping("/update/{bId}")
    public String update(@PathVariable String bId, Model model){
        commonProcess(model, "게시판 수정");

        Board board = boardConfigInfoService.get(bId, true);
        BoardForm boardForm = new ModelMapper().map(board, BoardForm.class);
        boardForm.setMode("update");
        boardForm.setListAccessRole(board.getListAccessRole().toString());
        boardForm.setViewAccessRole(board.getViewAccessRole().toString());
        boardForm.setWriteAccessRole(board.getWriteAccessRole().toString());
        boardForm.setReplyAccessRole(board.getReplyAccessRole().toString());
        boardForm.setCommentAccessRole(board.getCommentAccessRole().toString());

        model.addAttribute("boardForm", boardForm);

        return "admin/board/boardConfig";
    }

    @PostMapping("/save")
    public String save(@Valid BoardForm boardForm, Errors errors, Model model){
        String mode = boardForm.getMode();
        commonProcess(model, mode != null && mode.equals("update") ? "게시판 수정" : "게시판 등록");

        try {
            boardConfigSaveService.save(boardForm, errors);
        } catch (CommonException e) {
            e.printStackTrace();
            errors.reject("BoardConfigError", e.getMessage());
        }

        if (errors.hasErrors()) {
            return "admin/board/boardConfig";
        }

        return "redirect:/admin/board";
    }

    private void commonProcess(Model model, String title){
        // 서브 메뉴 코드
        String subMenuCode = Menus.getSubMenuCode(request);
        subMenuCode = title.equals("게시판 수정") ? "register" : subMenuCode;
        model.addAttribute("subMenuCode", subMenuCode);

        List<MenuForm> submenus = Menus.gets("board");
        model.addAttribute("submenus",submenus);

        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", "board");
    }

}
