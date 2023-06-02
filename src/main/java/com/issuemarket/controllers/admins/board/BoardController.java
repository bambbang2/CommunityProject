package com.issuemarket.controllers.admins.board;


import com.issuemarket.commons.MenuForm;
import com.issuemarket.commons.Menus;
import com.issuemarket.entities.BoardForm;
import com.issuemarket.exception.CommonException;
import com.issuemarket.service.admin.board.config.BoardConfigService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.EdECKey;
import java.util.List;

@Controller("adminBoardController")
@RequestMapping("admin/board")
@RequiredArgsConstructor
public class BoardController {

    private final HttpServletRequest request;

    private final BoardConfigService boardConfigService;

    @GetMapping
    public String index(Model model){
        commonProcess(model, "게시판 목록");

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

        return "admin/board/boardConfig";
    }

    @PostMapping("/save")
    public String save(@Valid BoardForm boardForm, Errors errors, Model model){
        String mode = boardForm.getMode();
        commonProcess(model, mode != null && mode.equals("update") ? "게시판 수정" : "게시판 등록");

        try {
            boardConfigService.save(boardForm, errors);
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
        model.addAttribute("subMenuCode", subMenuCode);

        List<MenuForm> submenus = Menus.gets("board");
        model.addAttribute("submenus",submenus);

        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", "board");
    }

}
